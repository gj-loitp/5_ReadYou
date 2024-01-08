package com.roy93group.reader.infrastructure.pref

import android.content.Context
import com.roy93group.reader.R
import com.roy93group.reader.ui.page.setting.acc.AccountViewModel

sealed class SyncOnlyOnWiFiPref(
    val value: Boolean,
) {

    object On : SyncOnlyOnWiFiPref(true)
    object Off : SyncOnlyOnWiFiPref(false)

    fun put(accountId: Int, viewModel: AccountViewModel) {
        viewModel.update(accountId) { syncOnlyOnWiFi = this@SyncOnlyOnWiFiPref }
    }

    fun toDesc(context: Context): String =
        when (this) {
            On -> context.getString(R.string.on)
            Off -> context.getString(R.string.off)
        }

    companion object {

        val default = Off
        val values = listOf(On, Off)
    }
}

operator fun SyncOnlyOnWiFiPref.not(): SyncOnlyOnWiFiPref =
    when (value) {
        true -> SyncOnlyOnWiFiPref.Off
        false -> SyncOnlyOnWiFiPref.On
    }
