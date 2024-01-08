package com.roy93group.reader.infrastructure.pref

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.roy93group.reader.ui.ext.DataStoreKeys
import com.roy93group.reader.ui.ext.dataStore
import com.roy93group.reader.ui.ext.put

sealed class AmoledDarkThemePref(val value: Boolean) : Pref() {
    object ON : AmoledDarkThemePref(true)
    object OFF : AmoledDarkThemePref(false)

    override fun put(context: Context, scope: CoroutineScope) {
        scope.launch {
            context.dataStore.put(
                DataStoreKeys.AmoledDarkTheme,
                value
            )
        }
    }

    companion object {

        val default = OFF
        val values = listOf(ON, OFF)

        fun fromPreferences(preferences: Preferences) =
            when (preferences[DataStoreKeys.AmoledDarkTheme.key]) {
                true -> ON
                false -> OFF
                else -> default
            }
    }
}

operator fun AmoledDarkThemePref.not(): AmoledDarkThemePref =
    when (value) {
        true -> AmoledDarkThemePref.OFF
        false -> AmoledDarkThemePref.ON
    }
