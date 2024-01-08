package com.roy93group.reader.infrastructure.pref

import android.content.Context
import com.roy93group.reader.R
import com.roy93group.reader.ui.page.setting.acc.AccountViewModel

sealed class SyncOnStartPref(
    val value: Boolean,
) {

    object On : SyncOnStartPref(true)
    object Off : SyncOnStartPref(false)

    fun put(accountId: Int, viewModel: AccountViewModel) {
        viewModel.update(accountId) { syncOnStart = this@SyncOnStartPref }
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

operator fun SyncOnStartPref.not(): SyncOnStartPref =
    when (value) {
        true -> SyncOnStartPref.Off
        false -> SyncOnStartPref.On
    }
