package com.mckimquyen.reader.domain.model.account

import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import com.mckimquyen.reader.infrastructure.pref.SyncOnlyWhenChargingPref

/**
 * Provide [TypeConverter] of [SyncOnlyWhenChargingPref] for [RoomDatabase].
 */
class SyncOnlyWhenChargingConverters {

    @TypeConverter
    fun toSyncOnlyWhenCharging(syncOnlyWhenCharging: Boolean): SyncOnlyWhenChargingPref {
        return SyncOnlyWhenChargingPref.values.find { it.value == syncOnlyWhenCharging }
            ?: SyncOnlyWhenChargingPref.default
    }

    @TypeConverter
    fun fromSyncOnlyWhenCharging(syncOnlyWhenCharging: SyncOnlyWhenChargingPref): Boolean {
        return syncOnlyWhenCharging.value
    }
}
