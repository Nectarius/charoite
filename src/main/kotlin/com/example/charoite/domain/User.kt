package com.example.charoite.domain

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class User(
    @BsonId
    val id: ObjectId? = null,
    val googleId: String,
    val email: String,
    val name: String,
    val avatarUrl: String?
)
