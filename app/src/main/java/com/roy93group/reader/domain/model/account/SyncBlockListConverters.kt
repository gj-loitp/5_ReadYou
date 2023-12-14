package com.roy93group.reader.domain.model.account

import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import com.roy93group.reader.infrastructure.preference.SyncBlockList
import com.roy93group.reader.infrastructure.preference.SyncBlockListPreference

/**
 * Provide [TypeConverter] of [SyncBlockListPreference] for [RoomDatabase].
 */
class SyncBlockListConverters {

    @TypeConverter
    fun toBlockList(syncBlockList: String): SyncBlockList =
        SyncBlockListPreference.of(syncBlockList)

    @TypeConverter
    fun fromBlockList(syncBlockList: SyncBlockList?): String =
        SyncBlockListPreference.toString(syncBlockList ?: emptyList())
}
