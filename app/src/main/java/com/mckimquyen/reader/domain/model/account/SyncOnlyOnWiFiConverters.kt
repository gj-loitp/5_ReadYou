package com.mckimquyen.reader.domain.model.account

import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import com.mckimquyen.reader.infrastructure.pref.SyncOnlyOnWiFiPref

/**
 * Provide [TypeConverter] of [SyncOnlyOnWiFiPref] for [RoomDatabase].
 */
class SyncOnlyOnWiFiConverters {

    @TypeConverter
    fun toSyncOnlyOnWiFi(syncOnlyOnWiFi: Boolean): SyncOnlyOnWiFiPref {
        return SyncOnlyOnWiFiPref.values.find { it.value == syncOnlyOnWiFi } ?: SyncOnlyOnWiFiPref.default
    }

    @TypeConverter
    fun fromSyncOnlyOnWiFi(syncOnlyOnWiFi: SyncOnlyOnWiFiPref): Boolean {
        return syncOnlyOnWiFi.value
    }
}
