package com.charoite.config

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MongoConfig(
    @Value("\${mongodb.uri}") private val uri: String,
    @Value("\${mongodb.db}") private val dbName: String
) {

    @Bean
    fun mongoClient(): MongoClient {
        return MongoClients.create(uri)
    }

    @Bean
    fun mongoDatabase(mongoClient: MongoClient): MongoDatabase {
        return mongoClient.getDatabase(if (dbName.isNotBlank()) dbName else "charoite")
    }
}
