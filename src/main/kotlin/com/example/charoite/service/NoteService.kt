package com.example.charoite.service

import com.example.charoite.domain.Note
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters.and
import com.mongodb.client.model.Filters.eq
import org.bson.types.ObjectId
import org.springframework.stereotype.Service

@Service
class NoteService(private val db: MongoDatabase) {

    private val collection: MongoCollection<Note>
        get() = db.getCollection("notes", Note::class.java)

    fun findNotesByUser(userId: String): List<Note> {
        return collection.find(eq("userId", userId))
            .sort(com.mongodb.client.model.Sorts.descending("createdAt"))
            .toList()
    }

    fun createNote(userId: String, title: String, content: String): Note {
        val note = Note(userId = userId, title = title, content = content)
        collection.insertOne(note)
        return note
    }

    fun getNote(userId: String, noteId: String): Note? {
        return collection.find(and(eq("_id", ObjectId(noteId)), eq("userId", userId))).firstOrNull()
    }

    fun updateNote(userId: String, noteId: String, title: String, content: String): Note? {
        val note = getNote(userId, noteId) ?: return null
        val updatedNote = note.copy(title = title, content = content)
        collection.replaceOne(eq("_id", ObjectId(noteId)), updatedNote)
        return updatedNote
    }

    fun deleteNote(userId: String, noteId: String) {
        collection.deleteOne(and(eq("_id", ObjectId(noteId)), eq("userId", userId)))
    }
}
