package com.example.notes2025.data.repository

import com.example.notes2025.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNoteStream(): Flow<List<Note>>

    suspend fun insertNotes(notes: List<Note>)

    suspend fun insertNote(note: Note): Long

    suspend fun deleteNotes(ids: List<Int>)

    suspend fun getNoteById(id: Int): Note?

    suspend fun saveNote(note: Note)

    suspend fun fetchPagedNotes(
        pageSize: Int,
        page: Int,
        additionalFetchOffset: Int = 0,
    ): List<Note>
}
