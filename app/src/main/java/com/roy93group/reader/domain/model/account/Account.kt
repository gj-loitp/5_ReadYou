package com.roy93group.reader.domain.model.account

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.roy93group.reader.domain.model.account.security.DESUtils
import com.roy93group.reader.infrastructure.preference.*
import java.util.*

/**
 * In the application, at least one account exists and different accounts
 * can have the same feeds and articles.
 */
@Keep
@Entity(tableName = "account")
data class Account(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo
    var name: String,
    @ColumnInfo
    var type: AccountType,
    @ColumnInfo
    var updateAt: Date? = null,
    @ColumnInfo
    var lastArticleId: String? = null,
    @ColumnInfo(defaultValue = "30")
    var syncInterval: SyncIntervalPreference = SyncIntervalPreference.default,
    @ColumnInfo(defaultValue = "0")
    var syncOnStart: SyncOnStartPreference = SyncOnStartPreference.default,
    @ColumnInfo(defaultValue = "0")
    var syncOnlyOnWiFi: SyncOnlyOnWiFiPreference = SyncOnlyOnWiFiPreference.default,
    @ColumnInfo(defaultValue = "0")
    var syncOnlyWhenCharging: SyncOnlyWhenChargingPreference = SyncOnlyWhenChargingPreference.default,
    @ColumnInfo(defaultValue = "2592000000")
    var keepArchived: KeepArchivedPreference = KeepArchivedPreference.default,
    @ColumnInfo(defaultValue = "")
    var syncBlockList: SyncBlockList = SyncBlockListPreference.default,
    @ColumnInfo(defaultValue = DESUtils.empty)
    var securityKey: String? = DESUtils.empty,
)
