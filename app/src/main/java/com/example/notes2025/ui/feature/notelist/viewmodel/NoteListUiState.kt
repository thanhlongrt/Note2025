package com.example.notes2025.ui.feature.notelist.viewmodel

import com.example.notes2025.ui.feature.notelist.uimodel.SelectableNote

sealed class NoteListUiState {
    data object Loading : NoteListUiState()

    data class Notes(
        val notes: List<SelectableNote> = emptyList(),
        val selectionState: SelectionState = SelectionState.Off,
    ) : NoteListUiState()
}

enum class SelectionState {
    On,
    Off,
}
