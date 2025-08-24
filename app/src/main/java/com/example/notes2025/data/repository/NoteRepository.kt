package com.example.notes2025.data.repository

import com.example.notes2025.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNotesStream(): Flow<List<Note>>

    suspend fun insertNotes(notes: List<Note>)

    suspend fun deleteNotes(ids: List<String>)

    suspend fun getNoteById(id: String): Note?

    suspend fun saveNotes(notes: List<Note>)
}
