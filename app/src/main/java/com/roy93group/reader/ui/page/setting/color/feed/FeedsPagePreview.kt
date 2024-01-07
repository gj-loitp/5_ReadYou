package com.roy93group.reader.ui.page.setting.color.feed

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.roy93group.reader.R
import com.roy93group.reader.domain.model.feed.Feed
import com.roy93group.reader.domain.model.general.Filter
import com.roy93group.reader.domain.model.group.Group
import com.roy93group.reader.infrastructure.preference.FeedsGroupListExpandPreference
import com.roy93group.reader.infrastructure.preference.FeedsGroupListTonalElevationPreference
import com.roy93group.reader.infrastructure.preference.FeedsTopBarTonalElevationPreference
import com.roy93group.reader.ui.component.FilterBar
import com.roy93group.reader.ui.component.base.FeedbackIconButton
import com.roy93group.reader.ui.ext.alphaLN
import com.roy93group.reader.ui.ext.surfaceColorAtElevation
import com.roy93group.reader.ui.page.home.feed.FeedItem
import com.roy93group.reader.ui.page.home.feed.GroupItem
import com.roy93group.reader.ui.theme.palette.onDark
import kotlin.math.ln

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedsPagePreview(
    topBarTonalElevation: FeedsTopBarTonalElevationPreference,
    groupListExpand: FeedsGroupListExpandPreference,
    groupListTonalElevation: FeedsGroupListTonalElevationPreference,
    filterBarStyle: Int,
    filterBarFilled: Boolean,
    filterBarPadding: Dp,
    filterBarTonalElevation: Dp,
) {
    var filter by remember { mutableStateOf(Filter.Unread) }
    val feedBadgeAlpha by remember { derivedStateOf { (ln(groupListTonalElevation.value + 1.4f) + 2f) / 100f } }
    val groupAlpha by remember { derivedStateOf { groupListTonalElevation.value.dp.alphaLN(weight = 1.2f) } }
    val groupIndicatorAlpha by remember {
        derivedStateOf {
            groupListTonalElevation.value.dp.alphaLN(
                weight = 1.4f
            )
        }
    }

    Column(
        modifier = Modifier
            .animateContentSize()
            .background(
                color = MaterialTheme.colorScheme.surfaceColorAtElevation(
                    groupListTonalElevation.value.dp
                ) onDark MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(24.dp)
            )
    ) {
        TopAppBar(
            title = {},
            navigationIcon = {
                FeedbackIconButton(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            },
            actions = {
                FeedbackIconButton(
                    imageVector = Icons.Rounded.Refresh,
                    contentDescription = stringResource(R.string.refresh),
                    tint = MaterialTheme.colorScheme.onSurface,
                )
                FeedbackIconButton(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = stringResource(R.string.subscribe),
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }, colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = Color.Transparent,
            )
        )
        Spacer(modifier = Modifier.height(12.dp))
        GroupItem(
            isEnded = { false },
            isExpanded = { groupListExpand.value },
            group = generateGroupPreview(),
            alpha = groupAlpha,
            indicatorAlpha = groupIndicatorAlpha,
        )
        FeedItemExpandSwitcher(
            groupAlpha = groupAlpha,
            feedBadgeAlpha = feedBadgeAlpha,
            isExpanded = groupListExpand.value
        )
        Spacer(modifier = Modifier.height(12.dp))
        FilterBar(
            filter = filter,
            filterBarStyle = filterBarStyle,
            filterBarFilled = filterBarFilled,
            filterBarPadding = filterBarPadding,
            filterBarTonalElevation = filterBarTonalElevation,
        ) {
            filter = it
        }
    }
}

@Stable
@Composable
fun FeedItemExpandSwitcher(groupAlpha: Float, feedBadgeAlpha: Float, isExpanded: Boolean) {
    FeedPreview(
        groupAlpha = groupAlpha,
        feedBadgeAlpha = feedBadgeAlpha,
        isExpanded = isExpanded
    )
}

@Stable
@Composable
fun FeedPreview(groupAlpha: Float, feedBadgeAlpha: Float, isExpanded: Boolean) {
    FeedItem(
        feed = generateFeedPreview(),
        alpha = groupAlpha,
        badgeAlpha = feedBadgeAlpha,
        isEnded = { true },
        isExpanded = { isExpanded }
    )
}

@Stable
@Composable
fun generateFeedPreview(): Feed =
    Feed(
        id = "",
        name = stringResource(R.string.preview_feed_name),
        icon = "",
        accountId = 0,
        groupId = "",
        url = "",
    ).apply {
        important = 100
    }

@Stable
@Composable
fun generateGroupPreview(): Group =
    Group(
        id = "",
        name = stringResource(R.string.defaults),
        accountId = 0,
    )
