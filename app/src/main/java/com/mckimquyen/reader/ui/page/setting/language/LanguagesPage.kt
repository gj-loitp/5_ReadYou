package com.mckimquyen.reader.ui.page.setting.language

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mckimquyen.reader.R
import com.mckimquyen.reader.infrastructure.pref.LanguagesPref
import com.mckimquyen.reader.infrastructure.pref.LocalLanguages
import com.mckimquyen.reader.infrastructure.pref.OpenLinkPref
import com.mckimquyen.reader.ui.component.base.Banner
import com.mckimquyen.reader.ui.component.base.DisplayText
import com.mckimquyen.reader.ui.component.base.FeedbackIconButton
import com.mckimquyen.reader.ui.component.base.BaseScaffold
import com.mckimquyen.reader.ui.ext.openURL
import com.mckimquyen.reader.ui.page.setting.SettingItem
import com.mckimquyen.reader.ui.theme.palette.onLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguagesPage(
    navController: NavHostController,
) {
    val context = LocalContext.current
    val languages = LocalLanguages.current
    val scope = rememberCoroutineScope()

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
//                item(key = languages.value) {
//                    DisplayText(text = stringResource(R.string.languages), desc = "")
//                    Spacer(modifier = Modifier.height(16.dp))
//                    Banner(
//                        title = stringResource(R.string.help_translate),
//                        icon = Icons.Outlined.Lightbulb,
//                        action = {
//                            Icon(
//                                imageVector = Icons.Outlined.KeyboardArrowRight,
//                                contentDescription = stringResource(R.string.go_to),
//                            )
//                        },
//                    ) {
//                        context.openURL(
//                            context.getString(R.string.translatable_url),
//                            OpenLinkPref.AutoPreferCustomTabs
//                        )
//                    }
//                    Spacer(modifier = Modifier.height(16.dp))
//                }
                item {
                    LanguagesPref.values.map {
                        SettingItem(
                            title = it.toDesc(context),
                            onClick = {
                                it.put(context, scope)
                            },
                        ) {
                            RadioButton(selected = it == languages, onClick = {
                                it.put(context, scope)
                            })
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
}
