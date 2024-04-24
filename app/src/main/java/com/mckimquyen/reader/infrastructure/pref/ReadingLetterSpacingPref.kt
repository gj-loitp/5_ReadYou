package com.mckimquyen.reader.infrastructure.pref

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.mckimquyen.reader.ui.ext.DataStoreKeys
import com.mckimquyen.reader.ui.ext.dataStore
import com.mckimquyen.reader.ui.ext.put

object ReadingLetterSpacingPref {

    const val default = 0.5

    fun put(context: Context, scope: CoroutineScope, value: Double) {
        scope.launch {
            context.dataStore.put(DataStoreKeys.ReadingLetterSpacing, value)
        }
    }

    fun fromPreferences(preferences: Preferences) =
        preferences[DataStoreKeys.ReadingLetterSpacing.key] ?: default
}
