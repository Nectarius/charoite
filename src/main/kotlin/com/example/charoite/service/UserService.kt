package com.example.charoite.service

import com.example.charoite.domain.User
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters.eq
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class UserService(private val db: MongoDatabase) {

    private val collection: MongoCollection<User>
        get() = db.getCollection("users", User::class.java)

    fun processOAuth2User(oauth2User: OAuth2User) {
        val googleId = oauth2User.attributes["sub"] as? String ?: oauth2User.name
        val email = oauth2User.attributes["email"] as? String ?: ""
        val name = oauth2User.attributes["name"] as? String ?: ""
        val avatarUrl = oauth2User.attributes["picture"] as? String

        val existingUser = collection.find(eq("googleId", googleId)).firstOrNull()

        if (existingUser == null) {
            val newUser = User(
                googleId = googleId,
                email = email,
                name = name,
                avatarUrl = avatarUrl
            )
            collection.insertOne(newUser)
        }
    }
}
