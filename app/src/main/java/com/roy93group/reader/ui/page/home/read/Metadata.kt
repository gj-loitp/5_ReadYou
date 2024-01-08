package com.roy93group.reader.ui.page.home.read

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.roy93group.reader.infrastructure.pref.LocalOpenLink
import com.roy93group.reader.infrastructure.pref.LocalOpenLinkSpecificBrowser
import com.roy93group.reader.infrastructure.pref.LocalReadingFonts
import com.roy93group.reader.infrastructure.pref.LocalReadingTitleAlign
import com.roy93group.reader.infrastructure.pref.LocalReadingTitleBold
import com.roy93group.reader.infrastructure.pref.LocalReadingTitleUpperCase
import com.roy93group.reader.ui.ext.formatAsString
import com.roy93group.reader.ui.ext.openURL
import com.roy93group.reader.ui.ext.roundClick
import java.util.Date

@Composable
fun Metadata(
    feedName: String,
    title: String,
    author: String? = null,
    link: String? = null,
    publishedDate: Date,
) {
    val context = LocalContext.current
    val titleBold = LocalReadingTitleBold.current
    val titleUpperCase = LocalReadingTitleUpperCase.current
    val titleAlign = LocalReadingTitleAlign.current
    val openLink = LocalOpenLink.current
    val openLinkSpecificBrowser = LocalOpenLinkSpecificBrowser.current
    val dateString = remember(publishedDate) {
        publishedDate.formatAsString(context, atHourMinute = true)
    }

    val titleUpperCaseString by remember { derivedStateOf { title.uppercase() } }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .roundClick {
                context.openURL(link, openLink, openLinkSpecificBrowser)
            }
            .padding(12.dp)
    ) {
        Text(
            modifier = Modifier
                .alpha(0.7f)
                .fillMaxWidth(),
            text = dateString,
            color = MaterialTheme.colorScheme.outline,
            style = MaterialTheme.typography.labelMedium.copy(
                fontFamily = LocalReadingFonts.current.asFontFamily(context),
            ),
            textAlign = titleAlign.toTextAlign(),
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = if (titleUpperCase.value) titleUpperCaseString else title,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.headlineLarge.copy(
                fontFamily = LocalReadingFonts.current.asFontFamily(context),
                fontWeight = if (titleBold.value) FontWeight.SemiBold else FontWeight.Normal,
            ),
            textAlign = titleAlign.toTextAlign(),
        )
        Spacer(modifier = Modifier.height(4.dp))
        author?.let {
            if (it.isNotEmpty()) {
                Text(
                    modifier = Modifier
                        .alpha(0.7f)
                        .fillMaxWidth(),
                    text = it,
                    color = MaterialTheme.colorScheme.outline,
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontFamily = LocalReadingFonts.current.asFontFamily(context),
                    ),
                    textAlign = titleAlign.toTextAlign(),
                )
            }
        }
        Text(
            modifier = Modifier
                .alpha(0.7f)
                .fillMaxWidth(),
            text = feedName,
            color = MaterialTheme.colorScheme.outline,
            style = MaterialTheme.typography.labelMedium.copy(
                fontFamily = LocalReadingFonts.current.asFontFamily(context),
            ),
            textAlign = titleAlign.toTextAlign(),
        )
    }
}
