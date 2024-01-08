package com.roy93group.reader.infrastructure.pref

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.roy93group.reader.ui.ext.DataStoreKeys
import com.roy93group.reader.ui.ext.dataStore
import com.roy93group.reader.ui.ext.put

sealed class FlowFilterBarFilledPref(val value: Boolean) : Pref() {
    object ON : FlowFilterBarFilledPref(true)
    object OFF : FlowFilterBarFilledPref(false)

    override fun put(context: Context, scope: CoroutineScope) {
        scope.launch {
            context.dataStore.put(
                DataStoreKeys.FlowFilterBarFilled,
                value
            )
        }
    }

    companion object {

        val default = OFF
        val values = listOf(ON, OFF)

        fun fromPreferences(preferences: Preferences) =
            when (preferences[DataStoreKeys.FlowFilterBarFilled.key]) {
                true -> ON
                false -> OFF
                else -> default
            }
    }
}

operator fun FlowFilterBarFilledPref.not(): FlowFilterBarFilledPref =
    when (value) {
        true -> FlowFilterBarFilledPref.OFF
        false -> FlowFilterBarFilledPref.ON
    }
