package com.example.notes2025.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.notes2025.data.local.database.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun queryNote(id: String): NoteEntity?

    @Upsert
    suspend fun upsert(entities: List<NoteEntity>)

    @Query("SELECT * FROM notes")
    fun getNotesStream(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes")
    suspend fun getNotes(): List<NoteEntity>

    @Query(
        value = """
            DELETE FROM notes
            WHERE id in (:ids)
        """,
    )
    suspend fun deleteNotes(ids: List<String>)
}
