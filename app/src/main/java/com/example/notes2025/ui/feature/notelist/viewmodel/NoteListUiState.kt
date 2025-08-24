package com.example.notes2025.ui.feature.notelist.viewmodel

import com.example.notes2025.Logger
import com.example.notes2025.ui.feature.notelist.uimodel.SelectableNote

data class NoteListUiState(
    val isLoading: Boolean = false,
    val notes: List<SelectableNote> = emptyList(),
    val selectionState: SelectionState = SelectionState.Off,
) {
    val allSelected: Boolean
        get() =
            notes.all { it.isSelected }.also {
                Logger.debug("allSelected: $it")
            }

    val selectedCount: Int
        get() =
            notes.count { it.isSelected }.also {
                Logger.debug("selectedCount: $it")
            }
}

enum class SelectionState {
    On,
    Off,
}
