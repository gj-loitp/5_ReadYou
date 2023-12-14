package com.roy93group.reader.infrastructure.preference

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.roy93group.reader.ui.ext.DataStoreKeys
import com.roy93group.reader.ui.ext.dataStore
import com.roy93group.reader.ui.ext.put

sealed class ReadingSubheadUpperCasePreference(val value: Boolean) : Preference() {
    object ON : ReadingSubheadUpperCasePreference(true)
    object OFF : ReadingSubheadUpperCasePreference(false)

    override fun put(context: Context, scope: CoroutineScope) {
        scope.launch {
            context.dataStore.put(
                DataStoreKeys.ReadingSubheadUpperCase,
                value
            )
        }
    }

    companion object {

        val default = OFF
        val values = listOf(ON, OFF)

        fun fromPreferences(preferences: Preferences) =
            when (preferences[DataStoreKeys.ReadingSubheadUpperCase.key]) {
                true -> ON
                false -> OFF
                else -> default
            }
    }
}

operator fun ReadingSubheadUpperCasePreference.not(): ReadingSubheadUpperCasePreference =
    when (value) {
        true -> ReadingSubheadUpperCasePreference.OFF
        false -> ReadingSubheadUpperCasePreference.ON
    }
