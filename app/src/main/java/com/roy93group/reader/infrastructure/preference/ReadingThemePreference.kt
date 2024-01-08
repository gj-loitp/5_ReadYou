package com.roy93group.reader.infrastructure.preference

import android.content.Context
import androidx.compose.runtime.Immutable
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.roy93group.reader.ui.ext.DataStoreKeys
import com.roy93group.reader.ui.ext.dataStore
import com.roy93group.reader.ui.ext.put

@Immutable
sealed class ReadingThemePreference(val value: Int) : Preference() {

    object MaterialYou : ReadingThemePreference(0)
    object Reeder : ReadingThemePreference(1)
    object Paper : ReadingThemePreference(2)
    object Custom : ReadingThemePreference(3)

    override fun put(context: Context, scope: CoroutineScope) {
        scope.launch {
            context.dataStore.put(DataStoreKeys.ReadingTheme, value)
        }
    }

    fun toDesc(context: Context): String =
        when (this) {
            MaterialYou -> "Material You"
            Reeder -> "Reeder"
            Paper -> "Paper"
            Custom -> "Custom"
        }

    fun applyTheme(context: Context, scope: CoroutineScope) {
        when (this) {
            MaterialYou -> {
                ReadingTitleBoldPref.default.put(context, scope)
                ReadingTitleUpperCasePref.default.put(context, scope)
                ReadingTitleAlignPref.default.put(context, scope)
                ReadingSubheadBoldPref.default.put(context, scope)
                ReadingSubheadUpperCasePref.default.put(context, scope)
                ReadingSubheadAlignPreference.default.put(context, scope)
                ReadingTextBoldPref.default.put(context, scope)
                ReadingTextHorizontalPaddingPref.put(context, scope,
                    ReadingTextHorizontalPaddingPref.default)
                ReadingTextAlignPref.default.put(context, scope)
                ReadingLetterSpacingPreference.put(context, scope, ReadingLetterSpacingPreference.default)
                ReadingTextFontSizePref.put(context, scope, ReadingTextFontSizePref.default)
                ReadingImageRoundedCornersPreference.put(context, scope, ReadingImageRoundedCornersPreference.default)
                ReadingImageHorizontalPaddingPreference.put(context, scope,
                    ReadingImageHorizontalPaddingPreference.default)
                ReadingImageMaximizePreference.default.put(context, scope)
            }

            Reeder -> {
                ReadingTitleBoldPref.ON.put(context, scope)
                ReadingTitleUpperCasePref.default.put(context, scope)
                ReadingTitleAlignPref.default.put(context, scope)
                ReadingSubheadBoldPref.ON.put(context, scope)
                ReadingSubheadUpperCasePref.default.put(context, scope)
                ReadingSubheadAlignPreference.default.put(context, scope)
                ReadingTextBoldPref.default.put(context, scope)
                ReadingTextHorizontalPaddingPref.put(context, scope,
                    ReadingTextHorizontalPaddingPref.default)
                ReadingTextAlignPref.default.put(context, scope)
                ReadingLetterSpacingPreference.put(context, scope, ReadingLetterSpacingPreference.default)
                ReadingTextFontSizePref.put(context, scope, 18)
                ReadingImageRoundedCornersPreference.put(context, scope, 0)
                ReadingImageHorizontalPaddingPreference.put(context, scope, 0)
                ReadingImageMaximizePreference.default.put(context, scope)
            }

            Paper -> {
                ReadingTitleBoldPref.ON.put(context, scope)
                ReadingTitleUpperCasePref.ON.put(context, scope)
                ReadingTitleAlignPref.Center.put(context, scope)
                ReadingSubheadBoldPref.ON.put(context, scope)
                ReadingSubheadUpperCasePref.ON.put(context, scope)
                ReadingSubheadAlignPreference.Center.put(context, scope)
                ReadingTextBoldPref.default.put(context, scope)
                ReadingTextHorizontalPaddingPref.put(context, scope,
                    ReadingTextHorizontalPaddingPref.default)
                ReadingTextAlignPref.Center.put(context, scope)
                ReadingLetterSpacingPreference.put(context, scope, ReadingLetterSpacingPreference.default)
                ReadingTextFontSizePref.put(context, scope, 20)
                ReadingImageRoundedCornersPreference.put(context, scope, 0)
                ReadingImageHorizontalPaddingPreference.put(context, scope,
                    ReadingImageHorizontalPaddingPreference.default)
                ReadingImageMaximizePreference.default.put(context, scope)
            }

            Custom -> {
                ReadingTitleBoldPref.default.put(context, scope)
                ReadingTitleUpperCasePref.default.put(context, scope)
                ReadingTitleAlignPref.default.put(context, scope)
                ReadingSubheadBoldPref.default.put(context, scope)
                ReadingSubheadUpperCasePref.default.put(context, scope)
                ReadingSubheadAlignPreference.default.put(context, scope)
                ReadingTextBoldPref.default.put(context, scope)
                ReadingTextHorizontalPaddingPref.put(context, scope,
                    ReadingTextHorizontalPaddingPref.default)
                ReadingTextAlignPref.default.put(context, scope)
                ReadingLetterSpacingPreference.put(context, scope, ReadingLetterSpacingPreference.default)
                ReadingTextFontSizePref.put(context, scope, ReadingTextFontSizePref.default)
                ReadingImageRoundedCornersPreference.put(context, scope, ReadingImageRoundedCornersPreference.default)
                ReadingImageHorizontalPaddingPreference.put(context, scope,
                    ReadingImageHorizontalPaddingPreference.default)
                ReadingImageMaximizePreference.default.put(context, scope)
            }
        }
    }

    companion object {

        val default = MaterialYou
        val values = listOf(MaterialYou, Reeder, Paper, Custom)

        fun fromPreferences(preferences: Preferences): ReadingThemePreference =
            when (preferences[DataStoreKeys.ReadingTheme.key]) {
                0 -> MaterialYou
                1 -> Reeder
                2 -> Paper
                3 -> Custom
                else -> default
            }
    }
}
