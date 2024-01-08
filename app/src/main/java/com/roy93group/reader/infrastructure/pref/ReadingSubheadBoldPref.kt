package com.roy93group.reader.infrastructure.pref

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import com.roy93group.reader.ui.ext.DataStoreKeys
import com.roy93group.reader.ui.ext.dataStore
import com.roy93group.reader.ui.ext.put
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

sealed class ReadingSubheadBoldPref(val value: Boolean) : Pref() {
    object ON : ReadingSubheadBoldPref(true)
    object OFF : ReadingSubheadBoldPref(false)

    override fun put(context: Context, scope: CoroutineScope) {
        scope.launch {
            context.dataStore.put(
                DataStoreKeys.ReadingSubheadBold,
                value
            )
        }
    }

    companion object {

        val default = OFF
        val values = listOf(ON, OFF)

        fun fromPreferences(preferences: Preferences) =
            when (preferences[DataStoreKeys.ReadingSubheadBold.key]) {
                true -> ON
                false -> OFF
                else -> default
            }
    }
}

operator fun ReadingSubheadBoldPref.not(): ReadingSubheadBoldPref =
    when (value) {
        true -> ReadingSubheadBoldPref.OFF
        false -> ReadingSubheadBoldPref.ON
    }
