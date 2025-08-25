package com.example.notes2025.data.repository

import com.example.notes2025.data.local.database.dao.NoteDao
import com.example.notes2025.data.local.database.entity.NoteEntity
import com.example.notes2025.model.Note
import com.example.notes2025.utils.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.collections.map

class DefaultNoteRepository
    @Inject
    constructor(
        private val noteDao: NoteDao,
    ) : NoteRepository {
        override fun getNoteStream(): Flow<List<Note>> =
            noteDao
                .getNotesStream()
                .map { noteEntities ->
                    noteEntities.map(NoteEntity::toNote)
                }

        override suspend fun insertNotes(notes: List<Note>) {
            noteDao.upsert(notes.map(::NoteEntity))
        }

        override suspend fun insertNote(note: Note): Long = noteDao.insert(NoteEntity(note))

        override suspend fun deleteNotes(ids: List<Int>) {
            noteDao.deleteNotes(ids)
        }

        override suspend fun getNoteById(id: Int): Note? = noteDao.queryNote(id)?.toNote()

        override suspend fun saveNote(note: Note) {
            noteDao.upsert(listOf(NoteEntity(note)))
        }

        override suspend fun fetchPagedNotes(
            pageSize: Int,
            page: Int,
            additionalFetchOffset: Int,
        ): List<Note> {
            val offset = page * pageSize + additionalFetchOffset
            Logger.debug("fetchPagedNotes: limit = $pageSize, offset = $offset ")
            return noteDao
                .getPagedNoteStream(limit = pageSize, offset = offset)
                .map(NoteEntity::toNote)
        }
    }
