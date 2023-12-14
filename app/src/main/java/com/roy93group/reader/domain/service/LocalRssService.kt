package com.roy93group.reader.domain.service

import android.content.Context
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import com.roy93group.reader.domain.repository.AccountDao
import com.roy93group.reader.domain.repository.ArticleDao
import com.roy93group.reader.domain.repository.FeedDao
import com.roy93group.reader.domain.repository.GroupDao
import com.roy93group.reader.infrastructure.di.DefaultDispatcher
import com.roy93group.reader.infrastructure.di.IODispatcher
import com.roy93group.reader.infrastructure.rss.RssHelper
import com.roy93group.reader.infrastructure.android.NotificationHelper
import javax.inject.Inject

class LocalRssService @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val articleDao: ArticleDao,
    private val feedDao: FeedDao,
    private val rssHelper: RssHelper,
    private val notificationHelper: NotificationHelper,
    private val accountDao: AccountDao,
    private val groupDao: GroupDao,
    @IODispatcher
    private val ioDispatcher: CoroutineDispatcher,
    @DefaultDispatcher
    private val defaultDispatcher: CoroutineDispatcher,
    workManager: WorkManager,
) : AbstractRssRepository(
    context, accountDao, articleDao, groupDao,
    feedDao, workManager, rssHelper, notificationHelper, ioDispatcher, defaultDispatcher
)
