package com.example.notes2025.data.repository

import com.example.notes2025.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNotesStream(): Flow<List<Note>>

    suspend fun insertNotes(notes: List<Note>)

    suspend fun deleteNotes(ids: List<String>)
}
