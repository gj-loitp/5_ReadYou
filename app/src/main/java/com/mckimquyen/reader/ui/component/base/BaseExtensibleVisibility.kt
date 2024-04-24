package com.mckimquyen.reader.ui.component.base

import androidx.compose.animation.*
import androidx.compose.runtime.Composable

@Composable
fun BaseExtensibleVisibility(
    visible: Boolean,
    content: @Composable AnimatedVisibilityScope.() -> Unit,
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically(),
        content = content,
    )
}
