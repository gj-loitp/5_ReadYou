package com.roy93group.reader.ui.page.home.feed

import androidx.annotation.Keep
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roy93group.reader.R
import com.roy93group.reader.domain.model.account.Account
import com.roy93group.reader.domain.service.AccountService
import com.roy93group.reader.domain.service.RssService
import com.roy93group.reader.infrastructure.android.AndroidStringsHelper
import com.roy93group.reader.infrastructure.di.DefaultDispatcher
import com.roy93group.reader.infrastructure.di.IODispatcher
import com.roy93group.reader.ui.page.home.FilterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedsViewModel @Inject constructor(
    private val accountService: AccountService,
    private val rssService: RssService,
    private val androidStringsHelper: AndroidStringsHelper,
    @DefaultDispatcher
    private val defaultDispatcher: CoroutineDispatcher,
    @IODispatcher
    private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _feedsUiState = MutableStateFlow(FeedsUiState())
    val feedsUiState: StateFlow<FeedsUiState> = _feedsUiState.asStateFlow()

    fun fetchAccount() {
        viewModelScope.launch(ioDispatcher) {
            _feedsUiState.update { it.copy(account = accountService.getCurrentAccount()) }
        }
    }

    fun pullFeeds(filterState: FilterState) {
        val isStarred = filterState.filter.isStarred()
        val isUnread = filterState.filter.isUnread()
        _feedsUiState.update {
            it.copy(
                importantSum = rssService.get().pullImportant(isStarred, isUnread)
                    .mapLatest {
                        (it["sum"] ?: 0).run {
                            androidStringsHelper.getQuantityString(
                                when {
                                    isStarred -> R.plurals.starred_desc
                                    isUnread -> R.plurals.unread_desc
                                    else -> R.plurals.all_desc
                                },
                                this,
                                this
                            )
                        }
                    }.flowOn(defaultDispatcher),
                groupWithFeedList = combine(
                    rssService.get().pullImportant(isStarred, isUnread),
                    rssService.get().pullFeeds()
                ) { importantMap, groupWithFeedList ->
                    val groupIterator = groupWithFeedList.iterator()
                    while (groupIterator.hasNext()) {
                        val groupWithFeed = groupIterator.next()
                        val groupImportant = importantMap[groupWithFeed.group.id] ?: 0
                        if ((isStarred || isUnread) && groupImportant == 0) {
                            groupIterator.remove()
                            continue
                        }
                        groupWithFeed.group.important = groupImportant
                        val feedIterator = groupWithFeed.feeds.iterator()
                        while (feedIterator.hasNext()) {
                            val feed = feedIterator.next()
                            val feedImportant = importantMap[feed.id] ?: 0
                            if ((isStarred || isUnread) && feedImportant == 0) {
                                feedIterator.remove()
                                continue
                            }
                            feed.important = feedImportant
                        }
                    }
                    groupWithFeedList
                }.mapLatest { groupWithFeedList ->
                    groupWithFeedList.map {
                        mutableListOf<GroupFeedsView>(GroupFeedsView.Group(it.group)).apply {
                            addAll(
                                it.feeds.map {
                                    GroupFeedsView.Feed(it)
                                }
                            )
                        }
                    }.flatten()
                }.flowOn(defaultDispatcher),
            )
        }
    }
}

@Keep
data class FeedsUiState(
    val account: Account? = null,
    val importantSum: Flow<String> = emptyFlow(),
    val groupWithFeedList: Flow<List<GroupFeedsView>> = emptyFlow(),
    val listState: LazyListState = LazyListState(),
    val groupsVisible: SnapshotStateMap<String, Boolean> = mutableStateMapOf(),
)

sealed class GroupFeedsView {
    class Group(val group: com.roy93group.reader.domain.model.group.Group) : GroupFeedsView()
    class Feed(val feed: com.roy93group.reader.domain.model.feed.Feed) : GroupFeedsView()
}