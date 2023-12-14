package com.roy93group.reader.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.roy93group.reader.R
import com.roy93group.reader.ui.component.base.TextFieldDialog

@Composable
fun RenameDialog(
    visible: Boolean = false,
    value: String = "",
    onValueChange: (String) -> Unit = {},
    onDismissRequest: () -> Unit = {},
    onConfirm: (String) -> Unit = {},
) {
    TextFieldDialog(
        visible = visible,
        title = stringResource(R.string.rename),
        icon = Icons.Outlined.Edit,
        value = value,
        placeholder = stringResource(R.string.name),
        onValueChange = onValueChange,
        onDismissRequest = onDismissRequest,
        onConfirm = onConfirm
    )
}
