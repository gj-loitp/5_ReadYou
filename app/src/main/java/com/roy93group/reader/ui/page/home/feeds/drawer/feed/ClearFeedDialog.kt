package com.roy93group.reader.ui.page.home.feeds.drawer.feed

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DeleteSweep
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.roy93group.reader.R
import com.roy93group.reader.ui.component.base.RYDialog
import com.roy93group.reader.ui.ext.collectAsStateValue
import com.roy93group.reader.ui.ext.showToast

@Composable
fun ClearFeedDialog(
    feedName: String,
    feedOptionViewModel: FeedOptionViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val feedOptionUiState = feedOptionViewModel.feedOptionUiState.collectAsStateValue()
    val scope = rememberCoroutineScope()
    val toastString = stringResource(R.string.clear_articles_in_feed_toast, feedName)

    RYDialog(
        visible = feedOptionUiState.clearDialogVisible,
        onDismissRequest = {
            feedOptionViewModel.hideClearDialog()
        },
        icon = {
            Icon(
                imageVector = Icons.Outlined.DeleteSweep,
                contentDescription = stringResource(R.string.clear_articles),
            )
        },
        title = {
            Text(text = stringResource(R.string.clear_articles))
        },
        text = {
            Text(text = stringResource(R.string.clear_articles_feed_tips, feedName))
        },
        confirmButton = {
            TextButton(
                onClick = {
                    feedOptionViewModel.clearFeed {
                        feedOptionViewModel.hideClearDialog()
                        feedOptionViewModel.hideDrawer(scope)
                        context.showToast(toastString)
                    }
                }
            ) {
                Text(
                    text = stringResource(R.string.clear),
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    feedOptionViewModel.hideClearDialog()
                }
            ) {
                Text(
                    text = stringResource(R.string.cancel),
                )
            }
        },
    )
}
