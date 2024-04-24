package com.mckimquyen.reader.infrastructure.pref

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.mckimquyen.reader.ui.ext.DataStoreKeys
import com.mckimquyen.reader.ui.ext.dataStore
import com.mckimquyen.reader.ui.ext.put

sealed class FeedsFilterBarFilledPref(val value: Boolean) : Pref() {
    object ON : FeedsFilterBarFilledPref(true)
    object OFF : FeedsFilterBarFilledPref(false)

    override fun put(context: Context, scope: CoroutineScope) {
        scope.launch {
            context.dataStore.put(
                DataStoreKeys.FeedsFilterBarFilled,
                value
            )
        }
    }

    companion object {

        val default = OFF
        val values = listOf(ON, OFF)

        fun fromPreferences(preferences: Preferences) =
            when (preferences[DataStoreKeys.FeedsFilterBarFilled.key]) {
                true -> ON
                false -> OFF
                else -> default
            }
    }
}

operator fun FeedsFilterBarFilledPref.not(): FeedsFilterBarFilledPref =
    when (value) {
        true -> FeedsFilterBarFilledPref.OFF
        false -> FeedsFilterBarFilledPref.ON
    }
