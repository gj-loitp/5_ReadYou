package com.roy93group.reader.ui.page.setting.color.reading

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.roy93group.reader.R
import com.roy93group.reader.infrastructure.preference.LocalReadingLetterSpacing
import com.roy93group.reader.infrastructure.preference.LocalReadingTextAlign
import com.roy93group.reader.infrastructure.preference.LocalReadingTextBold
import com.roy93group.reader.infrastructure.preference.LocalReadingTextFontSize
import com.roy93group.reader.infrastructure.preference.LocalReadingTextHorizontalPadding
import com.roy93group.reader.infrastructure.preference.LocalReadingTheme
import com.roy93group.reader.infrastructure.preference.ReadingLetterSpacingPreference
import com.roy93group.reader.infrastructure.preference.ReadingTextAlignPref
import com.roy93group.reader.infrastructure.preference.ReadingTextFontSizePref
import com.roy93group.reader.infrastructure.preference.ReadingTextHorizontalPaddingPref
import com.roy93group.reader.infrastructure.preference.ReadingThemePreference
import com.roy93group.reader.infrastructure.preference.not
import com.roy93group.reader.ui.component.base.DisplayText
import com.roy93group.reader.ui.component.base.FeedbackIconButton
import com.roy93group.reader.ui.component.base.BaseScaffold
import com.roy93group.reader.ui.component.base.BaseSwitch
import com.roy93group.reader.ui.component.base.RadioDlg
import com.roy93group.reader.ui.component.base.RadioDialogOption
import com.roy93group.reader.ui.component.base.Subtitle
import com.roy93group.reader.ui.component.base.TextFieldDlg
import com.roy93group.reader.ui.page.setting.SettingItem
import com.roy93group.reader.ui.theme.palette.onLight

@Composable
fun ReadingTextPage(
    navController: NavHostController,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val readingTheme = LocalReadingTheme.current
    val fontSize = LocalReadingTextFontSize.current
    val letterSpacing = LocalReadingLetterSpacing.current
    val horizontalPadding = LocalReadingTextHorizontalPadding.current
    val align = LocalReadingTextAlign.current
    val bold = LocalReadingTextBold.current

    var fontSizeDialogVisible by remember { mutableStateOf(false) }
    var letterSpacingDialogVisible by remember { mutableStateOf(false) }
    var horizontalPaddingDialogVisible by remember { mutableStateOf(false) }
    var alignDialogVisible by remember { mutableStateOf(false) }

    var fontSizeValue: Int? by remember { mutableStateOf(fontSize) }
    var letterSpacingValue: String? by remember { mutableStateOf(letterSpacing.toString()) }
    var horizontalPaddingValue: Int? by remember { mutableStateOf(horizontalPadding) }

    BaseScaffold(
        containerColor = MaterialTheme.colorScheme.surface onLight MaterialTheme.colorScheme.inverseOnSurface,
        navigationIcon = {
            FeedbackIconButton(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = stringResource(R.string.back),
                tint = MaterialTheme.colorScheme.onSurface
            ) {
                navController.popBackStack()
            }
        },
        content = {
            LazyColumn {
                item {
                    DisplayText(text = stringResource(R.string.text), desc = "")
                }

                // Preview
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                            .clip(RoundedCornerShape(24.dp))
                            .background(
                                MaterialTheme.colorScheme.inverseOnSurface
                                        onLight MaterialTheme.colorScheme.surface.copy(0.7f)
                            )
                            .clickable { },
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TitleAndTextPreview()
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }

                // Text
                item {
                    Subtitle(
                        modifier = Modifier.padding(horizontal = 24.dp),
                        text = stringResource(R.string.text)
                    )
                    SettingItem(
                        title = stringResource(R.string.font_size),
                        desc = "${fontSize}sp",
                        onClick = { fontSizeDialogVisible = true },
                    ) {}
                    SettingItem(
                        title = stringResource(R.string.bold),
                        onClick = {
                            (!bold).put(context, scope)
                            ReadingThemePreference.Custom.put(context, scope)
                        },
                    ) {
                        BaseSwitch(activated = bold.value) {
                            (!bold).put(context, scope)
                            ReadingThemePreference.Custom.put(context, scope)
                        }
                    }
                    SettingItem(
                        title = stringResource(R.string.letter_spacing),
                        desc = "${letterSpacing}sp",
                        onClick = { letterSpacingDialogVisible = true },
                    ) {}
                    SettingItem(
                        title = stringResource(R.string.horizontal_padding),
                        desc = "${horizontalPadding}dp",
                        onClick = { horizontalPaddingDialogVisible = true },
                    ) {}
                    SettingItem(
                        title = stringResource(R.string.alignment),
                        desc = align.toDesc(context),
                        onClick = { alignDialogVisible = true },
                    ) {}
                }

                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))
                }
            }
        }
    )

    TextFieldDlg(
        visible = fontSizeDialogVisible,
        title = stringResource(R.string.font_size),
        value = (fontSizeValue ?: "").toString(),
        placeholder = stringResource(R.string.value),
        onValueChange = {
            fontSizeValue = it.filter { it.isDigit() }.toIntOrNull()
        },
        onDismissRequest = {
            fontSizeDialogVisible = false
        },
        onConfirm = {
            ReadingTextFontSizePref.put(context, scope, fontSizeValue ?: 0)
            ReadingThemePreference.Custom.put(context, scope)
            fontSizeDialogVisible = false
        }
    )

    TextFieldDlg(
        visible = letterSpacingDialogVisible,
        title = stringResource(R.string.letter_spacing),
        value = (letterSpacingValue ?: "").toString(),
        placeholder = stringResource(R.string.value),
        onValueChange = {
            letterSpacingValue = it
        },
        onDismissRequest = {
            letterSpacingDialogVisible = false
        },
        onConfirm = {
            ReadingLetterSpacingPreference.put(context, scope, letterSpacingValue?.toDoubleOrNull() ?: 0.0)
            ReadingThemePreference.Custom.put(context, scope)
            letterSpacingDialogVisible = false
        }
    )

    TextFieldDlg(
        visible = horizontalPaddingDialogVisible,
        title = stringResource(R.string.horizontal_padding),
        value = (horizontalPaddingValue ?: "").toString(),
        placeholder = stringResource(R.string.value),
        onValueChange = {
            horizontalPaddingValue = it.filter { it.isDigit() }.toIntOrNull()
        },
        onDismissRequest = {
            horizontalPaddingDialogVisible = false
        },
        onConfirm = {
            ReadingTextHorizontalPaddingPref.put(context, scope, horizontalPaddingValue ?: 0)
            ReadingThemePreference.Custom.put(context, scope)
            horizontalPaddingDialogVisible = false
        }
    )

    RadioDlg(
        visible = alignDialogVisible,
        title = stringResource(R.string.alignment),
        options = ReadingTextAlignPref.values.map {
            RadioDialogOption(
                text = it.toDesc(context),
                selected = it == align,
            ) {
                it.put(context, scope)
                ReadingThemePreference.Custom.put(context, scope)
            }
        }
    ) {
        alignDialogVisible = false
    }
}
