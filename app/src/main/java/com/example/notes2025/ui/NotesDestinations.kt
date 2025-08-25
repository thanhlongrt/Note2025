package com.example.notes2025.ui

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed interface NotesDestination {
    val route: String
}

data object NoteListDestination : NotesDestination {
    override val route: String = "note_list"
}

data object NoteEditDestination : NotesDestination {
    override val route: String = "edit_note"
    const val NOTE_ID_ARG = "noteId"
    val routeWithArgs = "$route/{$NOTE_ID_ARG}"
    val arguments =
        listOf(
            navArgument(NOTE_ID_ARG) {
                type = NavType.IntType
            },
        )
}
