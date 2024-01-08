package com.roy93group.reader.infrastructure.preference

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.roy93group.reader.R
import com.roy93group.reader.ui.ext.DataStoreKeys
import com.roy93group.reader.ui.ext.dataStore
import com.roy93group.reader.ui.ext.put

sealed class InitialPagePref(val value: Int) : Pref() {
    object FeedsPage : InitialPagePref(0)
    object FlowPage : InitialPagePref(1)

    override fun put(context: Context, scope: CoroutineScope) {
        scope.launch {
            context.dataStore.put(
                DataStoreKeys.InitialPage,
                value
            )
        }
    }

    fun toDesc(context: Context): String =
        when (this) {
            FeedsPage -> context.getString(R.string.feeds_page)
            FlowPage -> context.getString(R.string.flow_page)
        }

    companion object {

        val default = FeedsPage
        val values = listOf(FeedsPage, FlowPage)

        fun fromPreferences(preferences: Preferences) =
            when (preferences[DataStoreKeys.InitialPage.key]) {
                0 -> FeedsPage
                1 -> FlowPage
                else -> default
            }
    }
}