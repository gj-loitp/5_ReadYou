package com.roy93group.reader.infrastructure.pref

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.roy93group.reader.ui.ext.DataStoreKeys
import com.roy93group.reader.ui.ext.dataStore
import com.roy93group.reader.ui.ext.put

sealed class ReadingImageMaximizePref(val value: Boolean) : Pref() {
    object ON : ReadingImageMaximizePref(true)
    object OFF : ReadingImageMaximizePref(false)

    override fun put(context: Context, scope: CoroutineScope) {
        scope.launch {
            context.dataStore.put(DataStoreKeys.ReadingImageMaximize, value)
        }
    }

    companion object {

        val default = ON
        val values = listOf(ON, OFF)

        fun fromPreferences(preferences: Preferences) =
            when (preferences[DataStoreKeys.ReadingImageMaximize.key]) {
                true -> ON
                false -> OFF
                else -> default
            }
    }
}

operator fun ReadingImageMaximizePref.not(): ReadingImageMaximizePref =
    when (value) {
        true -> ReadingImageMaximizePref.OFF
        false -> ReadingImageMaximizePref.ON
    }
