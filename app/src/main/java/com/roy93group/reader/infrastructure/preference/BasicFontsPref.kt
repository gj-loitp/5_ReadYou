package com.roy93group.reader.infrastructure.preference

import android.content.Context
import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontFamily
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.roy93group.reader.R
import com.roy93group.reader.ui.ext.DataStoreKeys
import com.roy93group.reader.ui.ext.ExternalFonts
import com.roy93group.reader.ui.ext.dataStore
import com.roy93group.reader.ui.ext.put
import com.roy93group.reader.ui.theme.SystemTypography

sealed class BasicFontsPref(val value: Int) : Pref() {
    object System : BasicFontsPref(0)
    object External : BasicFontsPref(5)

    override fun put(context: Context, scope: CoroutineScope) {
        scope.launch {
            context.dataStore.put(DataStoreKeys.BasicFonts, value)
        }
    }

    fun toDesc(context: Context): String =
        when (this) {
            System -> context.getString(R.string.system_default)
            External -> context.getString(R.string.external_fonts)
        }

    fun asFontFamily(context: Context): FontFamily =
        when (this) {
            System -> FontFamily.Default
            External -> ExternalFonts.loadBasicTypography(context).displayLarge.fontFamily ?: FontFamily.Default
        }

    fun asTypography(context: Context): Typography =
        when (this) {
            System -> SystemTypography
            External -> ExternalFonts.loadBasicTypography(context)
        }

    companion object {

        val default = System
        val values = listOf(System, External)

        fun fromPreferences(preferences: Preferences): BasicFontsPref =
            when (preferences[DataStoreKeys.BasicFonts.key]) {
                0 -> System
                5 -> External
                else -> default
            }
    }
}
