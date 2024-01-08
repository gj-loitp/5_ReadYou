package com.roy93group.reader.ui.page.home.feed.drawer.feed

import android.view.HapticFeedbackConstants
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CreateNewFolder
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.roy93group.reader.R
import com.roy93group.reader.infrastructure.pref.LocalOpenLink
import com.roy93group.reader.infrastructure.pref.LocalOpenLinkSpecificBrowser
import com.roy93group.reader.ui.component.ChangeUrlDialog
import com.roy93group.reader.ui.component.FeedIcon
import com.roy93group.reader.ui.component.RenameDialog
import com.roy93group.reader.ui.component.base.BottomDrawer
import com.roy93group.reader.ui.component.base.TextFieldDlg
import com.roy93group.reader.ui.ext.collectAsStateValue
import com.roy93group.reader.ui.ext.openURL
import com.roy93group.reader.ui.ext.roundClick
import com.roy93group.reader.ui.ext.showToast
import com.roy93group.reader.ui.page.home.feed.FeedOptionView
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FeedOptionDrawer(
    feedOptionViewModel: FeedOptionViewModel = hiltViewModel(),
    content: @Composable () -> Unit = {},
) {
    val context = LocalContext.current
    val view = LocalView.current
    val openLink = LocalOpenLink.current
    val openLinkSpecificBrowser = LocalOpenLinkSpecificBrowser.current
    val scope = rememberCoroutineScope()
    val feedOptionUiState = feedOptionViewModel.feedOptionUiState.collectAsStateValue()
    val feed = feedOptionUiState.feed
    val toastString = stringResource(R.string.rename_toast, feedOptionUiState.newName)

    BackHandler(feedOptionUiState.drawerState.isVisible) {
        scope.launch {
            feedOptionUiState.drawerState.hide()
        }
    }

    BottomDrawer(
        drawerState = feedOptionUiState.drawerState,
        sheetContent = {
            Column(modifier = Modifier.navigationBarsPadding()) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    FeedIcon(feedName = feed?.name ?: "", iconUrl = feed?.icon, size = 24.dp)
//                    Icon(
//                        modifier = Modifier.roundClick { },
//                        imageVector = Icons.Rounded.RssFeed,
//                        contentDescription = feed?.name ?: stringResource(R.string.unknown),
//                        tint = MaterialTheme.colorScheme.secondary,
//                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        modifier = Modifier.roundClick {
                            if (feedOptionViewModel.rssService.get().update) {
                                feedOptionViewModel.showRenameDialog()
                            }
                        },
                        text = feed?.name ?: stringResource(R.string.unknown),
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                FeedOptionView(
                    link = feed?.url ?: stringResource(R.string.unknown),
                    groups = feedOptionUiState.groups,
                    selectedAllowNotificationPreset = feedOptionUiState.feed?.isNotification
                        ?: false,
                    selectedParseFullContentPreset = feedOptionUiState.feed?.isFullContent ?: false,
                    isMoveToGroup = true,
                    showGroup = feedOptionViewModel.rssService.get().move,
                    showUnsubscribe = feedOptionViewModel.rssService.get().delete,
                    notSubscribeMode = true,
                    selectedGroupId = feedOptionUiState.feed?.groupId ?: "",
                    allowNotificationPresetOnClick = {
                        feedOptionViewModel.changeAllowNotificationPreset()
                    },
                    parseFullContentPresetOnClick = {
                        feedOptionViewModel.changeParseFullContentPreset()
                    },
                    clearArticlesOnClick = {
                        feedOptionViewModel.showClearDialog()
                    },
                    unsubscribeOnClick = {
                        feedOptionViewModel.showDeleteDialog()
                    },
                    onGroupClick = {
                        feedOptionViewModel.selectedGroup(it)
                    },
                    onAddNewGroup = {
                        feedOptionViewModel.showNewGroupDialog()
                    },
                    onFeedUrlClick = {
                        context.openURL(feed?.url, openLink, openLinkSpecificBrowser)
                    },
                    onFeedUrlLongClick = {
                        if (feedOptionViewModel.rssService.get().update) {
                            view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
                            feedOptionViewModel.showFeedUrlDialog()
                        }
                    }
                )
            }
        }
    ) {
        content()
    }

    DeleteFeedDialog(feedName = feed?.name ?: "")

    ClearFeedDialog(feedName = feed?.name ?: "")

    TextFieldDlg(
        visible = feedOptionUiState.newGroupDialogVisible,
        title = stringResource(R.string.create_new_group),
        icon = Icons.Outlined.CreateNewFolder,
        value = feedOptionUiState.newGroupContent,
        placeholder = stringResource(R.string.name),
        onValueChange = {
            feedOptionViewModel.inputNewGroup(it)
        },
        onDismissRequest = {
            feedOptionViewModel.hideNewGroupDialog()
        },
        onConfirm = {
            feedOptionViewModel.addNewGroup()
        }
    )

    RenameDialog(
        visible = feedOptionUiState.renameDialogVisible,
        value = feedOptionUiState.newName,
        onValueChange = {
            feedOptionViewModel.inputNewName(it)
        },
        onDismissRequest = {
            feedOptionViewModel.hideRenameDialog()
        },
        onConfirm = {
            feedOptionViewModel.renameFeed()
            feedOptionViewModel.hideDrawer(scope)
            context.showToast(toastString)
        }
    )

    ChangeUrlDialog(
        visible = feedOptionUiState.changeUrlDialogVisible,
        value = feedOptionUiState.newUrl,
        onValueChange = {
            feedOptionViewModel.inputNewUrl(it)
        },
        onDismissRequest = {
            feedOptionViewModel.hideFeedUrlDialog()
        },
        onConfirm = {
            feedOptionViewModel.changeFeedUrl()
            feedOptionViewModel.hideDrawer(scope)
        }
    )
}
