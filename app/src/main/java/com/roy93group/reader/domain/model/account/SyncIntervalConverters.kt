package com.roy93group.reader.domain.model.account

import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import com.roy93group.reader.infrastructure.pref.SyncIntervalPref

/**
 * Provide [TypeConverter] of [SyncIntervalPref] for [RoomDatabase].
 */
class SyncIntervalConverters {

    @TypeConverter
    fun toSyncInterval(syncInterval: Long): SyncIntervalPref {
        return SyncIntervalPref.values.find {
            it.value == syncInterval
        } ?: SyncIntervalPref.default
    }

    @TypeConverter
    fun fromSyncInterval(syncInterval: SyncIntervalPref): Long {
        return syncInterval.value
    }
}
