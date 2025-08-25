package com.example.notes2025.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.notes2025.data.local.database.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun queryNote(id: Int): NoteEntity?

    @Upsert
    suspend fun upsert(entities: List<NoteEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: NoteEntity): Long

    @Query("SELECT * FROM notes ORDER BY lastEdit DESC")
    fun getNotesStream(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes ORDER BY lastEdit DESC")
    suspend fun getNotes(): List<NoteEntity>

    @Query(
        value = """
            DELETE FROM notes
            WHERE id in (:ids)
        """,
    )
    suspend fun deleteNotes(ids: List<Int>)

    @Query("SELECT * FROM notes ORDER BY lastEdit DESC LIMIT :limit OFFSET :offset")
    suspend fun getPagedNoteStream(
        limit: Int,
        offset: Int,
    ): List<NoteEntity>
}
