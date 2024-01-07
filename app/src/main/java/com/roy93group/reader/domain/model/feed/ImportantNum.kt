package com.roy93group.reader.domain.model.feed

import androidx.annotation.Keep

/**
 * Counting the [important] number of articles in feeds and groups is generally
 * used in three situations.
 *
 * - Unread: Articles that have not been read yet
 * - Starred: Articles that have been marked as starred
 * - All: All articles
 */

@Keep
data class ImportantNum(
    val important: Int,
    val feedId: String,
    val groupId: String,
)
