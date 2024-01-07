package com.roy93group.reader.infrastructure.preference

import android.content.Context
import com.roy93group.reader.R
import com.roy93group.reader.ui.page.setting.acc.AccountViewModel

sealed class SyncOnlyWhenChargingPreference(
    val value: Boolean,
) {

    object On : SyncOnlyWhenChargingPreference(true)
    object Off : SyncOnlyWhenChargingPreference(false)

    fun put(accountId: Int, viewModel: AccountViewModel) {
        viewModel.update(accountId) { syncOnlyWhenCharging = this@SyncOnlyWhenChargingPreference }
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

operator fun SyncOnlyWhenChargingPreference.not(): SyncOnlyWhenChargingPreference =
    when (value) {
        true -> SyncOnlyWhenChargingPreference.Off
        false -> SyncOnlyWhenChargingPreference.On
    }
