package com.roy93group.reader.domain.model.article

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

/**
 * Data class for article metadata processing only.
 */

@Keep
data class ArticleMeta(
    @PrimaryKey
    var id: String,
    @ColumnInfo
    var isUnread: Boolean = true,
    @ColumnInfo
    var isStarred: Boolean = false,
)
