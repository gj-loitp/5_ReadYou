package com.roy93group.reader.domain.model.group

import androidx.annotation.Keep
import androidx.room.Embedded
import androidx.room.Relation
import com.roy93group.reader.domain.model.feed.Feed

/**
 * A [group] contains many [feeds].
 */
@Keep
data class GroupWithFeed(
    @Embedded
    var group: Group,
    @Relation(parentColumn = "id", entityColumn = "groupId")
    var feeds: MutableList<Feed>,
)
