package com.roy93group.reader.infrastructure.preference

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.roy93group.reader.ui.ext.DataStoreKeys
import com.roy93group.reader.ui.ext.dataStore
import com.roy93group.reader.ui.ext.put

object FlowFilterBarPaddingPref {

    const val default = 60

    fun put(context: Context, scope: CoroutineScope, value: Int) {
        scope.launch {
            context.dataStore.put(DataStoreKeys.FlowFilterBarPadding, value)
        }
    }

    fun fromPreferences(preferences: Preferences) =
        preferences[DataStoreKeys.FlowFilterBarPadding.key] ?: default
}
