package com.roy93group.reader.ui.page.setting.color.flow

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
import com.roy93group.reader.infrastructure.preference.FlowArticleListTonalElevationPreference
import com.roy93group.reader.infrastructure.preference.FlowFilterBarPaddingPreference
import com.roy93group.reader.infrastructure.preference.FlowFilterBarStylePreference
import com.roy93group.reader.infrastructure.preference.FlowFilterBarTonalElevationPreference
import com.roy93group.reader.infrastructure.preference.FlowTopBarTonalElevationPreference
import com.roy93group.reader.infrastructure.preference.LocalFlowArticleListDateStickyHeader
import com.roy93group.reader.infrastructure.preference.LocalFlowArticleListDesc
import com.roy93group.reader.infrastructure.preference.LocalFlowArticleListFeedIcon
import com.roy93group.reader.infrastructure.preference.LocalFlowArticleListFeedName
import com.roy93group.reader.infrastructure.preference.LocalFlowArticleListImage
import com.roy93group.reader.infrastructure.preference.LocalFlowArticleListTime
import com.roy93group.reader.infrastructure.preference.LocalFlowArticleListTonalElevation
import com.roy93group.reader.infrastructure.preference.LocalFlowFilterBarFilled
import com.roy93group.reader.infrastructure.preference.LocalFlowFilterBarPadding
import com.roy93group.reader.infrastructure.preference.LocalFlowFilterBarStyle
import com.roy93group.reader.infrastructure.preference.LocalFlowFilterBarTonalElevation
import com.roy93group.reader.infrastructure.preference.LocalFlowTopBarTonalElevation
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
fun FlowPageStylePage(
    navController: NavHostController,
) {
    val context = LocalContext.current
    val filterBarStyle = LocalFlowFilterBarStyle.current
    val filterBarFilled = LocalFlowFilterBarFilled.current
    val filterBarPadding = LocalFlowFilterBarPadding.current
    val filterBarTonalElevation = LocalFlowFilterBarTonalElevation.current
    val topBarTonalElevation = LocalFlowTopBarTonalElevation.current
    val articleListFeedIcon = LocalFlowArticleListFeedIcon.current
    val articleListFeedName = LocalFlowArticleListFeedName.current
    val articleListImage = LocalFlowArticleListImage.current
    val articleListDesc = LocalFlowArticleListDesc.current
    val articleListTime = LocalFlowArticleListTime.current
    val articleListStickyDate = LocalFlowArticleListDateStickyHeader.current
    val articleListTonalElevation = LocalFlowArticleListTonalElevation.current

    val scope = rememberCoroutineScope()

    var filterBarStyleDialogVisible by remember { mutableStateOf(false) }
    var filterBarPaddingDialogVisible by remember { mutableStateOf(false) }
    var filterBarTonalElevationDialogVisible by remember { mutableStateOf(false) }
    var topBarTonalElevationDialogVisible by remember { mutableStateOf(false) }
    var articleListTonalElevationDialogVisible by remember { mutableStateOf(false) }

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
                    DisplayText(text = stringResource(R.string.flow_page), desc = "")
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
                        FlowPagePreview(
                            topBarTonalElevation = topBarTonalElevation,
                            articleListTonalElevation = articleListTonalElevation,
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
                        title = stringResource(R.string.mark_as_read_button_position),
                        desc = stringResource(R.string.top),
                        enable = false,
                        onClick = {},
                    ) {}
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

                // Article List
                item {
                    Subtitle(
                        modifier = Modifier.padding(horizontal = 24.dp),
                        text = stringResource(R.string.article_list)
                    )
                    SettingItem(
                        title = stringResource(R.string.feed_favicons),
                        onClick = {
                            (!articleListFeedIcon).put(context, scope)
                        },
                    ) {
                        RYSwitch(activated = articleListFeedIcon.value) {
                            (!articleListFeedIcon).put(context, scope)
                        }
                    }
                    SettingItem(
                        title = stringResource(R.string.feed_names),
                        onClick = {
                            (!articleListFeedName).put(context, scope)
                        },
                    ) {
                        RYSwitch(activated = articleListFeedName.value) {
                            (!articleListFeedName).put(context, scope)
                        }
                    }
                    SettingItem(
                        title = stringResource(R.string.article_images),
                        onClick = {
                            (!articleListImage).put(context, scope)
                        },
                    ) {
                        RYSwitch(activated = articleListImage.value) {
                            (!articleListImage).put(context, scope)
                        }
                    }
                    SettingItem(
                        title = stringResource(R.string.article_desc),
                        onClick = {
                            (!articleListDesc).put(context, scope)
                        },
                    ) {
                        RYSwitch(activated = articleListDesc.value) {
                            (!articleListDesc).put(context, scope)
                        }
                    }
                    SettingItem(
                        title = stringResource(R.string.article_date),
                        onClick = {
                            (!articleListTime).put(context, scope)
                        },
                    ) {
                        RYSwitch(activated = articleListTime.value) {
                            (!articleListTime).put(context, scope)
                        }
                    }
                    SettingItem(
                        title = stringResource(R.string.article_date_sticky_header),
                        onClick = {
                            (!articleListStickyDate).put(context, scope)
                        },
                    ) {
                        RYSwitch(activated = articleListStickyDate.value) {
                            (!articleListStickyDate).put(context, scope)
                        }
                    }
                    SettingItem(
                        title = stringResource(R.string.tonal_elevation),
                        desc = "${articleListTonalElevation.value}dp",
                        onClick = {
                            articleListTonalElevationDialogVisible = true
                        },
                    ) {}
                    Tips(text = stringResource(R.string.tips_article_list_tonal_elevation))
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
                    Spacer(modifier = Modifier.height(24.dp))
                    Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))
                }
            }
        }
    )

    RadioDialog(
        visible = filterBarStyleDialogVisible,
        title = stringResource(R.string.style),
        options = FlowFilterBarStylePreference.values.map {
            RadioDialogOption(
                text = it.toDesc(context),
                selected = it == filterBarStyle,
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
            FlowFilterBarPaddingPreference.put(context, scope, filterBarPaddingValue ?: 0)
            filterBarPaddingDialogVisible = false
        }
    )

    RadioDialog(
        visible = filterBarTonalElevationDialogVisible,
        title = stringResource(R.string.tonal_elevation),
        options = FlowFilterBarTonalElevationPreference.values.map {
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
        options = FlowTopBarTonalElevationPreference.values.map {
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
        visible = articleListTonalElevationDialogVisible,
        title = stringResource(R.string.tonal_elevation),
        options = FlowArticleListTonalElevationPreference.values.map {
            RadioDialogOption(
                text = it.toDesc(context),
                selected = it == articleListTonalElevation,
            ) {
                it.put(context, scope)
            }
        }
    ) {
        articleListTonalElevationDialogVisible = false
    }
}
