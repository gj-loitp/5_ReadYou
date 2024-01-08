package com.roy93group.reader.infrastructure.pref

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import com.roy93group.reader.ui.ext.DataStoreKeys
import com.roy93group.reader.ui.ext.dataStore
import com.roy93group.reader.ui.ext.put
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

object CustomPrimaryColorPref {

    const val default = ""

    fun put(context: Context, scope: CoroutineScope, value: String) {
        scope.launch {
            context.dataStore.put(DataStoreKeys.CustomPrimaryColor, value)
        }
    }

    fun fromPreferences(preferences: Preferences) =
        preferences[DataStoreKeys.CustomPrimaryColor.key] ?: default
}
