package com.roy93group.reader.infrastructure.preference

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import com.roy93group.reader.ui.ext.DataStoreKeys
import com.roy93group.reader.ui.ext.dataStore
import com.roy93group.reader.ui.ext.put
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

sealed class ReadingAutoHideToolbarPreference(val value: Boolean) : Pref() {
    object ON : ReadingAutoHideToolbarPreference(true)
    object OFF : ReadingAutoHideToolbarPreference(false)

    override fun put(context: Context, scope: CoroutineScope) {
        scope.launch {
            context.dataStore.put(DataStoreKeys.ReadingAutoHideToolbar, value)
        }
    }

    companion object {

        val default = ON
        val values = listOf(ON, OFF)

        fun fromPreferences(preferences: Preferences) =
            when (preferences[DataStoreKeys.ReadingAutoHideToolbar.key]) {
                true -> ON
                false -> OFF
                else -> default
            }
    }
}

operator fun ReadingAutoHideToolbarPreference.not(): ReadingAutoHideToolbarPreference =
    when (value) {
        true -> ReadingAutoHideToolbarPreference.OFF
        false -> ReadingAutoHideToolbarPreference.ON
    }
