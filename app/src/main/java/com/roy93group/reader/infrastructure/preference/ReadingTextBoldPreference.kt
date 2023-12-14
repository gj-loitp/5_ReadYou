package com.roy93group.reader.infrastructure.preference

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.roy93group.reader.ui.ext.DataStoreKeys
import com.roy93group.reader.ui.ext.dataStore
import com.roy93group.reader.ui.ext.put

sealed class ReadingTextBoldPreference(val value: Boolean) : Preference() {
    object ON : ReadingTextBoldPreference(true)
    object OFF : ReadingTextBoldPreference(false)

    override fun put(context: Context, scope: CoroutineScope) {
        scope.launch {
            context.dataStore.put(
                DataStoreKeys.ReadingTextBold,
                value
            )
        }
    }

    companion object {

        val default = OFF
        val values = listOf(ON, OFF)

        fun fromPreferences(preferences: Preferences) =
            when (preferences[DataStoreKeys.ReadingTextBold.key]) {
                true -> ON
                false -> OFF
                else -> default
            }
    }
}

operator fun ReadingTextBoldPreference.not(): ReadingTextBoldPreference =
    when (value) {
        true -> ReadingTextBoldPreference.OFF
        false -> ReadingTextBoldPreference.ON
    }
