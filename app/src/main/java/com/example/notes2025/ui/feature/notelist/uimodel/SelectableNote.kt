package com.example.notes2025.ui.feature.notelist.uimodel

import com.example.notes2025.model.Note
import com.example.notes2025.utils.DateUtils

data class SelectableNote(
    val id: Int? = null,
    val title: String = "",
    val contents: String = "",
    val lastEdit: Long = DateUtils.currentTimeStamp(),
    val isSelected: Boolean = false,
) {
    val lastEditStr: String
        get() =
            DateUtils.dateLongToString(
                dateLong = lastEdit,
                format = DateUtils.FORMAT_ddMMyyyy_HHmm,
            )

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
