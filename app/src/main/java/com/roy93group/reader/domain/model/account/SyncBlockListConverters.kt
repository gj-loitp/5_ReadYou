package com.roy93group.reader.domain.model.account

import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import com.roy93group.reader.infrastructure.pref.SyncBlockList
import com.roy93group.reader.infrastructure.pref.SyncBlockListPref

/**
 * Provide [TypeConverter] of [SyncBlockListPref] for [RoomDatabase].
 */
class SyncBlockListConverters {

    @TypeConverter
    fun toBlockList(syncBlockList: String): SyncBlockList =
        SyncBlockListPref.of(syncBlockList)

    @TypeConverter
    fun fromBlockList(syncBlockList: SyncBlockList?): String =
        SyncBlockListPref.toString(syncBlockList ?: emptyList())
}
