package com.example.notes2025.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.notes2025.model.Note

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey() val id: String,
    val title: String,
    val contents: String,
    val lastEdit: Long = System.currentTimeMillis(),
) {
    constructor(note: Note) : this(
        id = note.id,
        title = note.title,
        contents = note.contents,
        lastEdit = note.lastEdit,
    )

    fun toNote(): Note =
        Note(
            id = id,
            title = title,
            contents = contents,
            lastEdit = lastEdit,
        )
}
