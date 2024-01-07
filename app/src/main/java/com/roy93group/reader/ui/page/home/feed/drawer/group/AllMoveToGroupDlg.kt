package com.roy93group.reader.ui.page.home.feed.drawer.group

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DriveFileMove
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
fun AllMoveToGroupDialog(
    groupName: String,
    groupOptionViewModel: GroupOptionViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val groupOptionUiState = groupOptionViewModel.groupOptionUiState.collectAsStateValue()
    val scope = rememberCoroutineScope()
    val toastString = stringResource(
        R.string.all_move_to_group_toast,
        groupOptionUiState.targetGroup?.name ?: ""
    )

    RYDialog(
        visible = groupOptionUiState.allMoveToGroupDialogVisible,
        onDismissRequest = {
            groupOptionViewModel.hideAllMoveToGroupDialog()
        },
        icon = {
            Icon(
                imageVector = Icons.Outlined.DriveFileMove,
                contentDescription = stringResource(R.string.move_to_group),
            )
        },
        title = {
            Text(text = stringResource(R.string.move_to_group))
        },
        text = {
            Text(
                text = stringResource(
                    R.string.all_move_to_group_tips,
                    groupName,
                    groupOptionUiState.targetGroup?.name ?: "",
                )
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    groupOptionViewModel.allMoveToGroup {
                        groupOptionViewModel.hideAllMoveToGroupDialog()
                        groupOptionViewModel.hideDrawer(scope)
                        context.showToast(toastString)
                    }
                }
            ) {
                Text(
                    text = stringResource(R.string.confirm),
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    groupOptionViewModel.hideAllMoveToGroupDialog()
                }
            ) {
                Text(
                    text = stringResource(R.string.cancel),
                )
            }
        },
    )
}