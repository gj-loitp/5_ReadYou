package com.roy93group.reader.infrastructure.preference

import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.roy93group.reader.R
import com.roy93group.reader.ui.ext.DataStoreKeys
import com.roy93group.reader.ui.ext.dataStore
import com.roy93group.reader.ui.ext.put

sealed class DarkThemePref(val value: Int) : Pref() {
    object UseDeviceTheme : DarkThemePref(0)
    object ON : DarkThemePref(1)
    object OFF : DarkThemePref(2)

    override fun put(context: Context, scope: CoroutineScope) {
        scope.launch {
            context.dataStore.put(
                DataStoreKeys.DarkTheme,
                value
            )
        }
    }

    fun toDesc(context: Context): String =
        when (this) {
            UseDeviceTheme -> context.getString(R.string.use_device_theme)
            ON -> context.getString(R.string.on)
            OFF -> context.getString(R.string.off)
        }

    @Composable
    @ReadOnlyComposable
    fun isDarkTheme(): Boolean = when (this) {
        UseDeviceTheme -> isSystemInDarkTheme()
        ON -> true
        OFF -> false
    }

    companion object {

        val default = UseDeviceTheme
        val values = listOf(UseDeviceTheme, ON, OFF)

        fun fromPreferences(preferences: Preferences) =
            when (preferences[DataStoreKeys.DarkTheme.key]) {
                0 -> UseDeviceTheme
                1 -> ON
                2 -> OFF
                else -> default
            }
    }
}

@Composable
operator fun DarkThemePref.not(): DarkThemePref =
    when (this) {
        DarkThemePref.UseDeviceTheme -> if (isSystemInDarkTheme()) {
            DarkThemePref.OFF
        } else {
            DarkThemePref.ON
        }

        DarkThemePref.ON -> DarkThemePref.OFF
        DarkThemePref.OFF -> DarkThemePref.ON
    }
