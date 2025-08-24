package com.example.notes2025.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notes2025.data.local.database.dao.NoteDao
import com.example.notes2025.data.local.database.entity.NoteEntity

@Database(
    entities = [
        NoteEntity::class,
    ],
    version = 1,
    exportSchema = true,
)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}
