package com.roy93group.reader.domain.model.account

import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import com.roy93group.reader.infrastructure.preference.SyncIntervalPreference

/**
 * Provide [TypeConverter] of [SyncIntervalPreference] for [RoomDatabase].
 */
class SyncIntervalConverters {

    @TypeConverter
    fun toSyncInterval(syncInterval: Long): SyncIntervalPreference {
        return SyncIntervalPreference.values.find { it.value == syncInterval } ?: SyncIntervalPreference.default
    }

    @TypeConverter
    fun fromSyncInterval(syncInterval: SyncIntervalPreference): Long {
        return syncInterval.value
    }
}
