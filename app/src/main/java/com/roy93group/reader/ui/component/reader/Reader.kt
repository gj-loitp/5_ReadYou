package com.roy93group.reader.ui.component.reader

import android.content.Context
import android.util.Log
import androidx.compose.foundation.lazy.LazyListScope
import com.roy93group.reader.R

@Suppress("FunctionName")
fun LazyListScope.Reader(
    context: Context,
    subheadUpperCase: Boolean = false,
    link: String,
    content: String,
    onLinkClick: (String) -> Unit,
) {
    Log.i("RLog", "Reader: ")
    htmlFormattedText(
        inputStream = content.byteInputStream(),
        subheadUpperCase = subheadUpperCase,
        baseUrl = link,
        imagePlaceholder = R.drawable.ic_launcher_foreground,
        onLinkClick = onLinkClick
    )
}
