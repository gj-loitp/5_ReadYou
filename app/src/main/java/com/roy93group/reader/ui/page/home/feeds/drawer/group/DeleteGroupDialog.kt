package com.roy93group.reader.ui.page.home.feeds.drawer.group

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DeleteForever
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
fun DeleteGroupDialog(
    groupName: String,
    groupOptionViewModel: GroupOptionViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val groupOptionUiState = groupOptionViewModel.groupOptionUiState.collectAsStateValue()
    val scope = rememberCoroutineScope()
    val toastString = stringResource(R.string.delete_toast, groupName)

    RYDialog(
        visible = groupOptionUiState.deleteDialogVisible,
        onDismissRequest = {
            groupOptionViewModel.hideDeleteDialog()
        },
        icon = {
            Icon(
                imageVector = Icons.Outlined.DeleteForever,
                contentDescription = stringResource(R.string.delete_group),
            )
        },
        title = {
            Text(text = stringResource(R.string.delete_group))
        },
        text = {
            Text(text = stringResource(R.string.delete_group_tips, groupName))
        },
        confirmButton = {
            TextButton(
                onClick = {
                    groupOptionViewModel.delete {
                        groupOptionViewModel.hideDeleteDialog()
                        groupOptionViewModel.hideDrawer(scope)
                        context.showToast(toastString)
                    }
                }
            ) {
                Text(
                    text = stringResource(R.string.delete),
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    groupOptionViewModel.hideDeleteDialog()
                }
            ) {
                Text(
                    text = stringResource(R.string.cancel),
                )
            }
        },
    )
}