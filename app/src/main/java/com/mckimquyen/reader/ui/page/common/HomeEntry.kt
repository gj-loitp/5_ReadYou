package com.mckimquyen.reader.ui.page.common

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mckimquyen.reader.domain.model.general.Filter
import com.mckimquyen.reader.infrastructure.pref.LocalDarkTheme
import com.mckimquyen.reader.infrastructure.pref.LocalReadingDarkTheme
import com.mckimquyen.reader.ui.ext.animatedComposable
import com.mckimquyen.reader.ui.ext.collectAsStateValue
import com.mckimquyen.reader.ui.ext.findActivity
import com.mckimquyen.reader.ui.ext.initialFilter
import com.mckimquyen.reader.ui.ext.initialPage
import com.mckimquyen.reader.ui.ext.isFirstLaunch
import com.mckimquyen.reader.ui.page.home.HomeViewModel
import com.mckimquyen.reader.ui.page.home.feed.FeedsPage
import com.mckimquyen.reader.ui.page.home.flow.FlowPage
import com.mckimquyen.reader.ui.page.home.read.ReadingPage
import com.mckimquyen.reader.ui.page.setting.SettingsPage
import com.mckimquyen.reader.ui.page.setting.acc.AccountDetailsPage
import com.mckimquyen.reader.ui.page.setting.acc.AccountsPage
import com.mckimquyen.reader.ui.page.setting.acc.AddAccountsPage
import com.mckimquyen.reader.ui.page.setting.color.ColorAndStylePage
import com.mckimquyen.reader.ui.page.setting.color.DarkThemePage
import com.mckimquyen.reader.ui.page.setting.color.feed.FeedsPageStylePage
import com.mckimquyen.reader.ui.page.setting.color.flow.FlowPageStylePage
import com.mckimquyen.reader.ui.page.setting.color.reading.ReadingDarkThemePage
import com.mckimquyen.reader.ui.page.setting.color.reading.ReadingImagePage
import com.mckimquyen.reader.ui.page.setting.color.reading.ReadingStylePage
import com.mckimquyen.reader.ui.page.setting.color.reading.ReadingTextPage
import com.mckimquyen.reader.ui.page.setting.color.reading.ReadingTitlePage
import com.mckimquyen.reader.ui.page.setting.color.reading.ReadingVideoPage
import com.mckimquyen.reader.ui.page.setting.interaction.InteractionPage
import com.mckimquyen.reader.ui.page.setting.language.LanguagesPage
import com.mckimquyen.reader.ui.page.setting.tip.TipsAndSupportPage
import com.mckimquyen.reader.ui.page.startup.StartupPage
import com.mckimquyen.reader.ui.theme.AppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalAnimationApi::class, androidx.compose.material.ExperimentalMaterialApi::class)
@Composable
fun HomeEntry(
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    var isReadingPage by rememberSaveable { mutableStateOf(false) }
    val filterUiState = homeViewModel.filterUiState.collectAsStateValue()
    val navController = rememberAnimatedNavController()

    val intent by rememberSaveable { mutableStateOf(context.findActivity()?.intent) }
    var openArticleId by rememberSaveable {
        mutableStateOf(intent?.extras?.get(ExtraName.ARTICLE_ID)?.toString() ?: "")
    }.also {
        intent?.replaceExtras(null)
    }

    LaunchedEffect(Unit) {
        when (context.initialPage) {
            1 -> {
                navController.navigate(RouteName.FLOW) {
                    launchSingleTop = true
                }
            }
            // Other initial pages
        }

        homeViewModel.changeFilter(
            filterUiState.copy(
                filter = when (context.initialFilter) {
                    0 -> Filter.Starred
                    1 -> Filter.Unread
                    2 -> Filter.All
                    else -> Filter.All
                }
            )
        )

        // This is finally
        navController.currentBackStackEntryFlow.collectLatest {
            Log.i("RLog", "currentBackStackEntry: ${navController.currentDestination?.route}")
            // Animation duration takes 310 ms
            delay(310L)
            isReadingPage = navController.currentDestination?.route == "${RouteName.READING}/{articleId}"
        }
    }

    LaunchedEffect(openArticleId) {
        if (openArticleId.isNotEmpty()) {
            navController.navigate(RouteName.FLOW) {
                launchSingleTop = true
            }
            navController.navigate("${RouteName.READING}/${openArticleId}") {
                launchSingleTop = true
            }
            openArticleId = ""
        }
    }

    val useDarkTheme = if (isReadingPage) {
        LocalReadingDarkTheme.current.isDarkTheme()
    } else {
        LocalDarkTheme.current.isDarkTheme()
    }

    AppTheme(
        useDarkTheme = if (isReadingPage) LocalReadingDarkTheme.current.isDarkTheme()
        else LocalDarkTheme.current.isDarkTheme()
    ) {

        rememberSystemUiController().run {
            setStatusBarColor(Color.Transparent, !useDarkTheme)
            setSystemBarsColor(Color.Transparent, !useDarkTheme)
            setNavigationBarColor(Color.Transparent, !useDarkTheme)
        }

        AnimatedNavHost(
            modifier = Modifier.background(MaterialTheme.colorScheme.surface),
            navController = navController,
            startDestination = if (context.isFirstLaunch) RouteName.STARTUP else RouteName.FEEDS,
        ) {
            // Startup
            animatedComposable(route = RouteName.STARTUP) {
                StartupPage(navController)
            }

            // Home
            animatedComposable(route = RouteName.FEEDS) {
                FeedsPage(navController = navController, homeViewModel = homeViewModel)
            }
            animatedComposable(route = RouteName.FLOW) {
                FlowPage(
                    navController = navController,
                    homeViewModel = homeViewModel,
                )
            }
            animatedComposable(route = "${RouteName.READING}/{articleId}") {
                ReadingPage(navController = navController, homeViewModel = homeViewModel)
            }

            // Settings
            animatedComposable(route = RouteName.SETTINGS) {
                SettingsPage(navController)
            }

            // Accounts
            animatedComposable(route = RouteName.ACCOUNTS) {
                AccountsPage(navController)
            }

            animatedComposable(route = "${RouteName.ACCOUNT_DETAILS}/{accountId}") {
                AccountDetailsPage(navController)
            }

            animatedComposable(route = RouteName.ADD_ACCOUNTS) {
                AddAccountsPage(navController)
            }

            // Color & Style
            animatedComposable(route = RouteName.COLOR_AND_STYLE) {
                ColorAndStylePage(navController)
            }
            animatedComposable(route = RouteName.DARK_THEME) {
                DarkThemePage(navController)
            }
            animatedComposable(route = RouteName.FEEDS_PAGE_STYLE) {
                FeedsPageStylePage(navController)
            }
            animatedComposable(route = RouteName.FLOW_PAGE_STYLE) {
                FlowPageStylePage(navController)
            }
            animatedComposable(route = RouteName.READING_PAGE_STYLE) {
                ReadingStylePage(navController)
            }
            animatedComposable(route = RouteName.READING_DARK_THEME) {
                ReadingDarkThemePage(navController)
            }
            animatedComposable(route = RouteName.READING_PAGE_TITLE) {
                ReadingTitlePage(navController)
            }
            animatedComposable(route = RouteName.READING_PAGE_TEXT) {
                ReadingTextPage(navController)
            }
            animatedComposable(route = RouteName.READING_PAGE_IMAGE) {
                ReadingImagePage(navController)
            }
            animatedComposable(route = RouteName.READING_PAGE_VIDEO) {
                ReadingVideoPage(navController)
            }

            // Interaction
            animatedComposable(route = RouteName.INTERACTION) {
                InteractionPage(navController)
            }

            // Languages
            animatedComposable(route = RouteName.LANGUAGES) {
                LanguagesPage(navController = navController)
            }

            // Tips & Support
            animatedComposable(route = RouteName.TIPS_AND_SUPPORT) {
                TipsAndSupportPage(navController)
            }
        }
    }
}
