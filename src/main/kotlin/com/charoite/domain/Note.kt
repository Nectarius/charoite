package com.charoite.domain

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import java.time.Instant

data class Note(
    @BsonId
    val id: ObjectId? = null,
    val userId: String, // Storing googleId for simplicity
    val title: String,
    val content: String,
    val createdAt: Instant = Instant.now()
)
