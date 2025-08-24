package com.example.notes2025.data.repository

import com.example.notes2025.data.local.database.dao.NoteDao
import com.example.notes2025.data.local.database.entity.NoteEntity
import com.example.notes2025.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultNoteRepository
    @Inject
    constructor(
        private val noteDao: NoteDao,
    ) : NoteRepository {
        override fun getNotesStream(): Flow<List<Note>> = noteDao.getNotesStream().map { it.map(NoteEntity::toNote) }

        override suspend fun insertNotes(notes: List<Note>) {
            noteDao.upsert(notes.map(::NoteEntity))
        }

        override suspend fun deleteNotes(ids: List<String>) {
            noteDao.deleteNotes(ids)
        }

        override suspend fun getNoteById(id: String): Note? = noteDao.queryNote(id)?.toNote()

        override suspend fun saveNotes(notes: List<Note>) {
            noteDao.upsert(notes.map(::NoteEntity))
        }
    }
