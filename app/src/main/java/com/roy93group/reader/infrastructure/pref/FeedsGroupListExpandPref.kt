package com.roy93group.reader.infrastructure.pref

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.roy93group.reader.ui.ext.DataStoreKeys
import com.roy93group.reader.ui.ext.dataStore
import com.roy93group.reader.ui.ext.put

sealed class FeedsGroupListExpandPref(val value: Boolean) : Pref() {
    object ON : FeedsGroupListExpandPref(true)
    object OFF : FeedsGroupListExpandPref(false)

    override fun put(context: Context, scope: CoroutineScope) {
        scope.launch {
            context.dataStore.put(
                DataStoreKeys.FeedsGroupListExpand,
                value
            )
        }
    }

    companion object {

        val default = ON
        val values = listOf(ON, OFF)

        fun fromPreferences(preferences: Preferences) =
            when (preferences[DataStoreKeys.FeedsGroupListExpand.key]) {
                true -> ON
                false -> OFF
                else -> default
            }
    }
}

operator fun FeedsGroupListExpandPref.not(): FeedsGroupListExpandPref =
    when (value) {
        true -> FeedsGroupListExpandPref.OFF
        false -> FeedsGroupListExpandPref.ON
    }
