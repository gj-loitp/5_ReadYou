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
import com.roy93group.reader.infrastructure.preference.LocalReadingSubheadAlign
import com.roy93group.reader.infrastructure.preference.LocalReadingSubheadBold
import com.roy93group.reader.infrastructure.preference.LocalReadingSubheadUpperCase
import com.roy93group.reader.infrastructure.preference.LocalReadingTitleAlign
import com.roy93group.reader.infrastructure.preference.LocalReadingTitleBold
import com.roy93group.reader.infrastructure.preference.LocalReadingTitleUpperCase
import com.roy93group.reader.infrastructure.preference.ReadingSubheadAlignPreference
import com.roy93group.reader.infrastructure.preference.ReadingThemePreference
import com.roy93group.reader.infrastructure.preference.ReadingTitleAlignPreference
import com.roy93group.reader.infrastructure.preference.not
import com.roy93group.reader.ui.component.base.DisplayText
import com.roy93group.reader.ui.component.base.FeedbackIconButton
import com.roy93group.reader.ui.component.base.RYScaffold
import com.roy93group.reader.ui.component.base.RYSwitch
import com.roy93group.reader.ui.component.base.RadioDialog
import com.roy93group.reader.ui.component.base.RadioDialogOption
import com.roy93group.reader.ui.component.base.Subtitle
import com.roy93group.reader.ui.page.setting.SettingItem
import com.roy93group.reader.ui.theme.palette.onLight

@Composable
fun ReadingTitlePage(
    navController: NavHostController,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val titleBold = LocalReadingTitleBold.current
    val subtitleBold = LocalReadingSubheadBold.current
    val titleAlign = LocalReadingTitleAlign.current
    val subtitleAlign = LocalReadingSubheadAlign.current
    val titleUpperCase = LocalReadingTitleUpperCase.current
    val subheadUpperCase = LocalReadingSubheadUpperCase.current

    var titleAlignDialogVisible by remember { mutableStateOf(false) }
    var subtitleAlignDialogVisible by remember { mutableStateOf(false) }

    RYScaffold(
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
                    DisplayText(text = stringResource(R.string.title), desc = "")
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

                // Title
                item {
                    Subtitle(
                        modifier = Modifier.padding(horizontal = 24.dp),
                        text = stringResource(R.string.title)
                    )
                    SettingItem(
                        title = stringResource(R.string.bold),
                        onClick = {
                            (!titleBold).put(context, scope)
                            ReadingThemePreference.Custom.put(context, scope)
                        },
                    ) {
                        RYSwitch(activated = titleBold.value) {
                            (!titleBold).put(context, scope)
                            ReadingThemePreference.Custom.put(context, scope)
                        }
                    }
                    SettingItem(
                        title = stringResource(R.string.upper_case),
                        onClick = {
                            (!titleUpperCase).put(context, scope)
                            ReadingThemePreference.Custom.put(context, scope)
                        },
                    ) {
                        RYSwitch(activated = titleUpperCase.value) {
                            (!titleUpperCase).put(context, scope)
                            ReadingThemePreference.Custom.put(context, scope)
                        }
                    }
                    SettingItem(
                        title = stringResource(R.string.alignment),
                        desc = titleAlign.toDesc(context),
                        onClick = { titleAlignDialogVisible = true },
                    ) {}
                    Spacer(modifier = Modifier.height(24.dp))
                }

                // Subhead
                item {
                    Subtitle(
                        modifier = Modifier.padding(horizontal = 24.dp),
                        text = stringResource(R.string.subhead)
                    )
                    SettingItem(
                        title = stringResource(R.string.bold),
                        onClick = {
                            (!subtitleBold).put(context, scope)
                            ReadingThemePreference.Custom.put(context, scope)
                        },
                    ) {
                        RYSwitch(activated = subtitleBold.value) {
                            (!subtitleBold).put(context, scope)
                            ReadingThemePreference.Custom.put(context, scope)
                        }
                    }
                    SettingItem(
                        title = stringResource(R.string.upper_case),
                        onClick = {
                            (!subheadUpperCase).put(context, scope)
                            ReadingThemePreference.Custom.put(context, scope)
                        },
                    ) {
                        RYSwitch(activated = subheadUpperCase.value) {
                            (!subheadUpperCase).put(context, scope)
                            ReadingThemePreference.Custom.put(context, scope)
                        }
                    }
                    SettingItem(
                        title = stringResource(R.string.alignment),
                        desc = subtitleAlign.toDesc(context),
                        enable = false,
                        onClick = {
//                            subtitleAlignDialogVisible = true
                        },
                    ) {}
                }

                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))
                }
            }
        }
    )

    RadioDialog(
        visible = titleAlignDialogVisible,
        title = stringResource(R.string.alignment),
        options = ReadingTitleAlignPreference.values.map {
            RadioDialogOption(
                text = it.toDesc(context),
                selected = it == titleAlign,
            ) {
                it.put(context, scope)
                ReadingThemePreference.Custom.put(context, scope)
            }
        }
    ) {
        titleAlignDialogVisible = false
    }

    RadioDialog(
        visible = subtitleAlignDialogVisible,
        title = stringResource(R.string.alignment),
        options = ReadingSubheadAlignPreference.values.map {
            RadioDialogOption(
                text = it.toDesc(context),
                selected = it == subtitleAlign,
            ) {
                it.put(context, scope)
                ReadingThemePreference.Custom.put(context, scope)
            }
        }
    ) {
        subtitleAlignDialogVisible = false
    }
}
