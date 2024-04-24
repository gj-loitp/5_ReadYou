package com.mckimquyen.reader.domain.model.article

import androidx.annotation.Keep
import androidx.room.Embedded
import androidx.room.Relation
import com.mckimquyen.reader.domain.model.feed.Feed

/**
 * An [article] contains a [feed].
 */
@Keep
data class ArticleWithFeed(
    @Embedded
    var article: Article,
    @Relation(parentColumn = "feedId", entityColumn = "id")
    var feed: Feed,
)
