package com.charoite.service

import com.charoite.domain.Comment
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import org.springframework.stereotype.Service

@Service
class CommentService(private val db: MongoDatabase) {

    private val collection: MongoCollection<Comment>
        get() = db.getCollection("comments", Comment::class.java)

    fun findAllComments(): List<Comment> {
        return collection.find()
            .sort(com.mongodb.client.model.Sorts.descending("createdAt"))
            .toList()
    }

    fun createComment(userId: String, userName: String, userAvatarUrl: String?, text: String): Comment {
        val comment = Comment(
            userId = userId,
            userName = userName,
            userAvatarUrl = userAvatarUrl,
            text = text
        )
        collection.insertOne(comment)
        return comment
    }

    fun getComment(userId: String, commentId: String): Comment? {
        return collection.find(com.mongodb.client.model.Filters.and(
            com.mongodb.client.model.Filters.eq("_id", org.bson.types.ObjectId(commentId)),
            com.mongodb.client.model.Filters.eq("userId", userId)
        )).firstOrNull()
    }

    fun updateComment(userId: String, commentId: String, text: String): Comment? {
        val comment = getComment(userId, commentId) ?: return null
        val updatedComment = comment.copy(text = text)
        collection.replaceOne(com.mongodb.client.model.Filters.eq("_id", org.bson.types.ObjectId(commentId)), updatedComment)
        return updatedComment
    }

    fun deleteComment(userId: String, commentId: String) {
        collection.deleteOne(com.mongodb.client.model.Filters.and(
            com.mongodb.client.model.Filters.eq("_id", org.bson.types.ObjectId(commentId)),
            com.mongodb.client.model.Filters.eq("userId", userId)
        ))
    }
}
