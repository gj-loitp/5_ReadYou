package com.roy93group.reader.infrastructure.pref

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.roy93group.reader.ui.ext.DataStoreKeys
import com.roy93group.reader.ui.ext.dataStore
import com.roy93group.reader.ui.ext.put

sealed class FlowArticleListImagePref(val value: Boolean) : Pref() {
    object ON : FlowArticleListImagePref(true)
    object OFF : FlowArticleListImagePref(false)

    override fun put(context: Context, scope: CoroutineScope) {
        scope.launch {
            context.dataStore.put(
                DataStoreKeys.FlowArticleListImage,
                value
            )
        }
    }

    companion object {

        val default = ON
        val values = listOf(ON, OFF)

        fun fromPreferences(preferences: Preferences) =
            when (preferences[DataStoreKeys.FlowArticleListImage.key]) {
                true -> ON
                false -> OFF
                else -> default
            }
    }
}

operator fun FlowArticleListImagePref.not(): FlowArticleListImagePref =
    when (value) {
        true -> FlowArticleListImagePref.OFF
        false -> FlowArticleListImagePref.ON
    }
