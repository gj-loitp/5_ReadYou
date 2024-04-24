package com.mckimquyen.reader.infrastructure.pref

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.LocalContext
import com.mckimquyen.reader.domain.repository.AccountDao
import com.mckimquyen.reader.ui.ext.collectAsStateValue
import com.mckimquyen.reader.ui.ext.currentAccountId

// Accounts
val LocalSyncInterval = compositionLocalOf<SyncIntervalPref> { SyncIntervalPref.default }
val LocalSyncOnStart = compositionLocalOf<SyncOnStartPref> { SyncOnStartPref.default }
val LocalSyncOnlyOnWiFi = compositionLocalOf<SyncOnlyOnWiFiPref> { SyncOnlyOnWiFiPref.default }
val LocalSyncOnlyWhenCharging =
    compositionLocalOf<SyncOnlyWhenChargingPref> { SyncOnlyWhenChargingPref.default }
val LocalKeepArchived = compositionLocalOf<KeepArchivedPreference> { KeepArchivedPreference.default }
val LocalSyncBlockList = compositionLocalOf { SyncBlockListPref.default }

@Composable
fun AccountSettingsProvider(
    accountDao: AccountDao,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    val accountSettings = accountDao.queryAccount(context.currentAccountId).collectAsStateValue(initial = null)

    CompositionLocalProvider(
        // Accounts
        LocalSyncInterval provides (accountSettings?.syncInterval ?: SyncIntervalPref.default),
        LocalSyncOnStart provides (accountSettings?.syncOnStart ?: SyncOnStartPref.default),
        LocalSyncOnlyOnWiFi provides (accountSettings?.syncOnlyOnWiFi ?: SyncOnlyOnWiFiPref.default),
        LocalSyncOnlyWhenCharging provides (accountSettings?.syncOnlyWhenCharging
            ?: SyncOnlyWhenChargingPref.default),
        LocalKeepArchived provides (accountSettings?.keepArchived ?: KeepArchivedPreference.default),
        LocalSyncBlockList provides (accountSettings?.syncBlockList ?: SyncBlockListPref.default),
    ) {
        content()
    }
}
