package com.roy93group.reader.ui.page.setting.color.reading

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.roy93group.reader.R
import com.roy93group.reader.infrastructure.preference.LocalReadingImageHorizontalPadding
import com.roy93group.reader.infrastructure.preference.LocalReadingImageMaximize
import com.roy93group.reader.infrastructure.preference.LocalReadingImageRoundedCorners
import com.roy93group.reader.infrastructure.preference.LocalReadingTheme
import com.roy93group.reader.infrastructure.preference.ReadingImageHorizontalPaddingPreference
import com.roy93group.reader.infrastructure.preference.ReadingImageRoundedCornersPreference
import com.roy93group.reader.infrastructure.preference.ReadingThemePreference
import com.roy93group.reader.infrastructure.preference.not
import com.roy93group.reader.ui.component.base.DisplayText
import com.roy93group.reader.ui.component.base.FeedbackIconButton
import com.roy93group.reader.ui.component.base.RYScaffold
import com.roy93group.reader.ui.component.base.RYSwitch
import com.roy93group.reader.ui.component.base.Subtitle
import com.roy93group.reader.ui.component.base.TextFieldDlg
import com.roy93group.reader.ui.page.setting.SettingItem
import com.roy93group.reader.ui.theme.palette.onLight

@Composable
fun ReadingImagePage(
    navController: NavHostController,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val readingTheme = LocalReadingTheme.current
    val roundedCorners = LocalReadingImageRoundedCorners.current
    val horizontalPadding = LocalReadingImageHorizontalPadding.current
    val maximize = LocalReadingImageMaximize.current

    var roundedCornersDialogVisible by remember { mutableStateOf(false) }
    var horizontalPaddingDialogVisible by remember { mutableStateOf(false) }

    var roundedCornersValue: Int? by remember { mutableStateOf(roundedCorners) }
    var horizontalPaddingValue: Int? by remember { mutableStateOf(horizontalPadding) }

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
                    DisplayText(text = stringResource(R.string.images), desc = "")
                }

                // Preview
                item {
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(horizontal = 24.dp)
//                            .clip(RoundedCornerShape(24.dp))
//                            .background(
//                                MaterialTheme.colorScheme.inverseOnSurface
//                                        onLight MaterialTheme.colorScheme.surface.copy(0.7f)
//                            )
//                            .clickable { },
//                        horizontalArrangement = Arrangement.Center,
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        RYAsyncImage(
//                            modifier = Modifier
//                                .fillMaxSize()
//                                .padding(24.dp)
//                                .padding(imageHorizontalPadding().dp)
//                                .clip(imageShape()),
//                            data = "https://images.unsplash.com/photo-1544716278-ca5e3f4abd8c?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1yZWxhdGVkfDJ8fHxlbnwwfHx8fA%3D%3D&auto=format&fit=crop&w=800&q=60",
//                            contentDescription = stringResource(R.string.images),
//                            contentScale = ContentScale.Inside,
//                        )
//                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }


                // Images
                item {
                    Subtitle(
                        modifier = Modifier.padding(horizontal = 24.dp),
                        text = stringResource(R.string.images)
                    )
                    SettingItem(
                        title = stringResource(R.string.rounded_corners),
                        desc = "${roundedCorners}dp",
                        onClick = { roundedCornersDialogVisible = true },
                    ) {}
                    SettingItem(
                        title = stringResource(R.string.horizontal_padding),
                        desc = "${horizontalPadding}dp",
                        onClick = { horizontalPaddingDialogVisible = true },
                    ) {}
                    SettingItem(
                        title = stringResource(R.string.maximize),
                        onClick = {
                            (!maximize).put(context, scope)
                            ReadingThemePreference.Custom.put(context, scope)
                        },
                    ) {
                        RYSwitch(activated = maximize.value) {
                            (!maximize).put(context, scope)
                            ReadingThemePreference.Custom.put(context, scope)
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))
                }
            }
        }
    )

    TextFieldDlg(
        visible = roundedCornersDialogVisible,
        title = stringResource(R.string.rounded_corners),
        value = (roundedCornersValue ?: "").toString(),
        placeholder = stringResource(R.string.value),
        onValueChange = {
            roundedCornersValue = it.filter { it.isDigit() }.toIntOrNull()
        },
        onDismissRequest = {
            roundedCornersDialogVisible = false
        },
        onConfirm = {
            ReadingImageRoundedCornersPreference.put(context, scope, roundedCornersValue ?: 0)
            ReadingThemePreference.Custom.put(context, scope)
            roundedCornersDialogVisible = false
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
            ReadingImageHorizontalPaddingPreference.put(context, scope, horizontalPaddingValue ?: 0)
            ReadingThemePreference.Custom.put(context, scope)
            horizontalPaddingDialogVisible = false
        }
    )
}
