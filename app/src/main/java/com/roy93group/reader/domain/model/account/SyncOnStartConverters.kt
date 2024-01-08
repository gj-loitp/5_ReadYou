package com.roy93group.reader.domain.model.account

import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import com.roy93group.reader.infrastructure.pref.SyncOnStartPref

/**
 * Provide [TypeConverter] of [SyncOnStartPref] for [RoomDatabase].
 */
class SyncOnStartConverters {

    @TypeConverter
    fun toSyncOnStart(syncOnStart: Boolean): SyncOnStartPref {
        return SyncOnStartPref.values.find { it.value == syncOnStart } ?: SyncOnStartPref.default
    }

    @TypeConverter
    fun fromSyncOnStart(syncOnStart: SyncOnStartPref): Boolean {
        return syncOnStart.value
    }
}
