package com.roy93group.reader.ui.page.setting.color.feed

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
import com.roy93group.reader.infrastructure.preference.FeedsFilterBarPaddingPreference
import com.roy93group.reader.infrastructure.preference.FeedsFilterBarStylePreference
import com.roy93group.reader.infrastructure.preference.FeedsFilterBarTonalElevationPreference
import com.roy93group.reader.infrastructure.preference.FeedsGroupListTonalElevationPreference
import com.roy93group.reader.infrastructure.preference.FeedsTopBarTonalElevationPreference
import com.roy93group.reader.infrastructure.preference.LocalFeedsFilterBarFilled
import com.roy93group.reader.infrastructure.preference.LocalFeedsFilterBarPadding
import com.roy93group.reader.infrastructure.preference.LocalFeedsFilterBarStyle
import com.roy93group.reader.infrastructure.preference.LocalFeedsFilterBarTonalElevation
import com.roy93group.reader.infrastructure.preference.LocalFeedsGroupListExpand
import com.roy93group.reader.infrastructure.preference.LocalFeedsGroupListTonalElevation
import com.roy93group.reader.infrastructure.preference.LocalFeedsTopBarTonalElevation
import com.roy93group.reader.infrastructure.preference.not
import com.roy93group.reader.ui.component.base.DisplayText
import com.roy93group.reader.ui.component.base.FeedbackIconButton
import com.roy93group.reader.ui.component.base.RYScaffold
import com.roy93group.reader.ui.component.base.RYSwitch
import com.roy93group.reader.ui.component.base.RadioDialog
import com.roy93group.reader.ui.component.base.RadioDialogOption
import com.roy93group.reader.ui.component.base.Subtitle
import com.roy93group.reader.ui.component.base.TextFieldDlg
import com.roy93group.reader.ui.component.base.Tips
import com.roy93group.reader.ui.page.setting.SettingItem
import com.roy93group.reader.ui.theme.palette.onLight

@Composable
fun FeedsPageStylePage(
    navController: NavHostController,
) {
    val context = LocalContext.current
    val filterBarStyle = LocalFeedsFilterBarStyle.current
    val filterBarFilled = LocalFeedsFilterBarFilled.current
    val filterBarPadding = LocalFeedsFilterBarPadding.current
    val filterBarTonalElevation = LocalFeedsFilterBarTonalElevation.current
    val topBarTonalElevation = LocalFeedsTopBarTonalElevation.current
    val groupListExpand = LocalFeedsGroupListExpand.current
    val groupListTonalElevation = LocalFeedsGroupListTonalElevation.current

    val scope = rememberCoroutineScope()

    var filterBarStyleDialogVisible by remember { mutableStateOf(false) }
    var filterBarPaddingDialogVisible by remember { mutableStateOf(false) }
    var filterBarTonalElevationDialogVisible by remember { mutableStateOf(false) }
    var topBarTonalElevationDialogVisible by remember { mutableStateOf(false) }
    var groupListTonalElevationDialogVisible by remember { mutableStateOf(false) }

    var filterBarPaddingValue: Int? by remember { mutableStateOf(filterBarPadding) }

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
                    DisplayText(text = stringResource(R.string.feeds_page), desc = "")
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
                        FeedsPagePreview(
                            topBarTonalElevation = topBarTonalElevation,
                            groupListExpand = groupListExpand,
                            groupListTonalElevation = groupListTonalElevation,
                            filterBarStyle = filterBarStyle.value,
                            filterBarFilled = filterBarFilled.value,
                            filterBarPadding = filterBarPadding.dp,
                            filterBarTonalElevation = filterBarTonalElevation.value.dp,
                        )
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }

                // Top Bar
                item {
                    Subtitle(
                        modifier = Modifier.padding(horizontal = 24.dp),
                        text = stringResource(R.string.top_bar)
                    )
                    SettingItem(
                        title = stringResource(R.string.tonal_elevation),
                        desc = "${topBarTonalElevation.value}dp",
                        onClick = {
                            topBarTonalElevationDialogVisible = true
                        },
                    ) {}
//                    Tips(text = stringResource(R.string.tips_top_bar_tonal_elevation))
                    Spacer(modifier = Modifier.height(24.dp))
                }

                // Group List
                item {
                    Subtitle(
                        modifier = Modifier.padding(horizontal = 24.dp),
                        text = stringResource(R.string.group_list)
                    )
                    SettingItem(
                        title = stringResource(R.string.always_expand),
                        onClick = {
                            (!groupListExpand).put(context, scope)
                        },
                    ) {
                        RYSwitch(activated = groupListExpand.value) {
                            (!groupListExpand).put(context, scope)
                        }
                    }
                    SettingItem(
                        title = stringResource(R.string.tonal_elevation),
                        desc = "${groupListTonalElevation.value}dp",
                        onClick = {
                            groupListTonalElevationDialogVisible = true
                        },
                    ) {}
                    Tips(text = stringResource(R.string.tips_group_list_tonal_elevation))
                    Spacer(modifier = Modifier.height(24.dp))
                }

                // Filter Bar
                item {
                    Subtitle(
                        modifier = Modifier.padding(horizontal = 24.dp),
                        text = stringResource(R.string.filter_bar),
                    )
                    SettingItem(
                        title = stringResource(R.string.style),
                        desc = filterBarStyle.toDesc(context),
                        onClick = {
                            filterBarStyleDialogVisible = true
                        },
                    ) {}
                    SettingItem(
                        title = stringResource(R.string.fill_selected_icon),
                        onClick = {
                            (!filterBarFilled).put(context, scope)
                        },
                    ) {
                        RYSwitch(activated = filterBarFilled.value) {
                            (!filterBarFilled).put(context, scope)
                        }
                    }
                    SettingItem(
                        title = stringResource(R.string.horizontal_padding),
                        desc = "${filterBarPadding}dp",
                        onClick = {
                            filterBarPaddingValue = filterBarPadding
                            filterBarPaddingDialogVisible = true
                        },
                    ) {}
                    SettingItem(
                        title = stringResource(R.string.tonal_elevation),
                        desc = "${filterBarTonalElevation.value}dp",
                        onClick = {
                            filterBarTonalElevationDialogVisible = true
                        },
                    ) {}
                }
                item {
                    Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))
                }
            }
        }
    )

    RadioDialog(
        visible = filterBarStyleDialogVisible,
        title = stringResource(R.string.style),
        options = FeedsFilterBarStylePreference.values.map {
            RadioDialogOption(
                text = it.toDesc(context),
                selected = filterBarStyle == it,
            ) {
                it.put(context, scope)
            }
        }
    ) {
        filterBarStyleDialogVisible = false
    }

    TextFieldDlg(
        visible = filterBarPaddingDialogVisible,
        title = stringResource(R.string.horizontal_padding),
        value = (filterBarPaddingValue ?: "").toString(),
        placeholder = stringResource(R.string.value),
        onValueChange = {
            filterBarPaddingValue = it.filter { it.isDigit() }.toIntOrNull()
        },
        onDismissRequest = {
            filterBarPaddingDialogVisible = false
        },
        onConfirm = {
            FeedsFilterBarPaddingPreference.put(context, scope, filterBarPaddingValue ?: 0)
            filterBarPaddingDialogVisible = false
        }
    )

    RadioDialog(
        visible = filterBarTonalElevationDialogVisible,
        title = stringResource(R.string.tonal_elevation),
        options = FeedsFilterBarTonalElevationPreference.values.map {
            RadioDialogOption(
                text = it.toDesc(context),
                selected = it == filterBarTonalElevation,
            ) {
                it.put(context, scope)
            }
        }
    ) {
        filterBarTonalElevationDialogVisible = false
    }

    RadioDialog(
        visible = topBarTonalElevationDialogVisible,
        title = stringResource(R.string.tonal_elevation),
        options = FeedsTopBarTonalElevationPreference.values.map {
            RadioDialogOption(
                text = it.toDesc(context),
                selected = it == topBarTonalElevation,
            ) {
                it.put(context, scope)
            }
        }
    ) {
        topBarTonalElevationDialogVisible = false
    }

    RadioDialog(
        visible = groupListTonalElevationDialogVisible,
        title = stringResource(R.string.tonal_elevation),
        options = FeedsGroupListTonalElevationPreference.values.map {
            RadioDialogOption(
                text = it.toDesc(context),
                selected = it == groupListTonalElevation,
            ) {
                it.put(context, scope)
            }
        }
    ) {
        groupListTonalElevationDialogVisible = false
    }
}
