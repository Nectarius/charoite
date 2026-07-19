package com.example.charoite.domain

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import java.time.Instant

data class Comment(
    @BsonId
    val id: ObjectId? = null,
    val userId: String,
    val userName: String,
    val userAvatarUrl: String?,
    val text: String,
    val createdAt: Instant = Instant.now()
)
