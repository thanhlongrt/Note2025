package com.example.notes2025.ui.feature.noteedit.uimodel

import com.example.notes2025.model.Note
import com.example.notes2025.utils.DateUtils

// Might add more fields later
data class EditableNote(
    val id: Int? = null,
    val title: String = "",
    val contents: String = "",
    val lastEdit: Long = System.currentTimeMillis(),
) {
    constructor(note: Note) : this(
        id = note.id,
        title = note.title,
        contents = note.contents,
        lastEdit = note.lastEdit,
    )

    fun toNote() =
        Note(
            id = id,
            title = title.ifBlank { "Note ${DateUtils.getDateStringToday()}" },
            contents = contents,
            lastEdit = lastEdit,
        )
}
