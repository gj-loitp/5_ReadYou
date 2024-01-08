package com.roy93group.reader.infrastructure.pref

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.roy93group.reader.ui.ext.DataStoreKeys
import com.roy93group.reader.ui.ext.dataStore
import com.roy93group.reader.ui.ext.put

sealed class FlowArticleListFeedNamePref(val value: Boolean) : Pref() {
    object ON : FlowArticleListFeedNamePref(true)
    object OFF : FlowArticleListFeedNamePref(false)

    override fun put(context: Context, scope: CoroutineScope) {
        scope.launch {
            context.dataStore.put(
                DataStoreKeys.FlowArticleListFeedName,
                value
            )
        }
    }

    companion object {

        val default = ON
        val values = listOf(ON, OFF)

        fun fromPreferences(preferences: Preferences) =
            when (preferences[DataStoreKeys.FlowArticleListFeedName.key]) {
                true -> ON
                false -> OFF
                else -> default
            }
    }
}

operator fun FlowArticleListFeedNamePref.not(): FlowArticleListFeedNamePref =
    when (value) {
        true -> FlowArticleListFeedNamePref.OFF
        false -> FlowArticleListFeedNamePref.ON
    }
