package com.mckimquyen.reader.infrastructure.pref

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.mckimquyen.reader.ui.ext.DataStoreKeys
import com.mckimquyen.reader.ui.ext.dataStore
import com.mckimquyen.reader.ui.ext.put

sealed class FlowArticleListDateStickyHeaderPref(val value: Boolean) : Pref() {
    object ON : FlowArticleListDateStickyHeaderPref(true)
    object OFF : FlowArticleListDateStickyHeaderPref(false)

    override fun put(context: Context, scope: CoroutineScope) {
        scope.launch {
            context.dataStore.put(
                DataStoreKeys.FlowArticleListDateStickyHeader,
                value
            )
        }
    }

    companion object {

        val default = ON
        val values = listOf(ON, OFF)

        fun fromPreferences(preferences: Preferences) =
            when (preferences[DataStoreKeys.FlowArticleListDateStickyHeader.key]) {
                true -> ON
                false -> OFF
                else -> default
            }
    }
}

operator fun FlowArticleListDateStickyHeaderPref.not(): FlowArticleListDateStickyHeaderPref =
    when (value) {
        true -> FlowArticleListDateStickyHeaderPref.OFF
        false -> FlowArticleListDateStickyHeaderPref.ON
    }
