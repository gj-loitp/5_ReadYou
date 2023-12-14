package com.roy93group.reader.infrastructure.preference

import android.content.Context
import com.roy93group.reader.R
import com.roy93group.reader.ui.page.settings.accounts.AccountViewModel

sealed class SyncOnStartPreference(
    val value: Boolean,
) {

    object On : SyncOnStartPreference(true)
    object Off : SyncOnStartPreference(false)

    fun put(accountId: Int, viewModel: AccountViewModel) {
        viewModel.update(accountId) { syncOnStart = this@SyncOnStartPreference }
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

operator fun SyncOnStartPreference.not(): SyncOnStartPreference =
    when (value) {
        true -> SyncOnStartPreference.Off
        false -> SyncOnStartPreference.On
    }
