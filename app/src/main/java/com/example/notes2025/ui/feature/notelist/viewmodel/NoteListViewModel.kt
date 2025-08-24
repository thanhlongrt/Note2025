package com.example.notes2025.ui.feature.notelist.viewmodel

import androidx.lifecycle.ViewModel
import com.example.notes2025.utils.DummyDataProvider
import com.example.notes2025.ui.feature.notelist.uimodel.SelectableNote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel
    @Inject
    constructor(
        // TODO: Add dependencies here
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(NoteListUiState())
        val uiState = _uiState.asStateFlow()
        private val currentState get() = _uiState.value

        init {
            fetchNotes()
        }

        fun fetchNotes() {
            _uiState.value = NoteListUiState(notes = DummyDataProvider.dummyNotes())
        }

        fun onNoteClick(note: SelectableNote) {
            if (currentState.selectionState == SelectionState.On) {
                _uiState.value =
                    currentState.copy(
                        notes = currentState.notes.toggleSelection(note),
                    )
            } else {
                // TODO: open NoteEditScreen
            }
        }

        fun onNoteLongClick(note: SelectableNote) {
            if (currentState.selectionState == SelectionState.On) return
            _uiState.value =
                currentState.copy(
                    selectionState = SelectionState.On,
                    notes = currentState.notes.toggleSelection(note),
                )
        }

        fun clearSelection() {
            _uiState.value =
                currentState.copy(
                    selectionState = SelectionState.Off,
                    notes = currentState.notes.toggleAllSelection(isSelected = false),
                )
        }

        fun toggleAllSelection() {
            _uiState.value =
                currentState.copy(
                    notes = currentState.notes.toggleAllSelection(),
                )
        }

        fun deleteSelectedNotes() {
            // TODO: delete selected notes
        }

        private fun List<SelectableNote>.toggleSelection(note: SelectableNote): List<SelectableNote> =
            map {
                if (it.id == note.id) {
                    it.copy(isSelected = !it.isSelected)
                } else {
                    it
                }
            }

        private fun List<SelectableNote>.toggleAllSelection(): List<SelectableNote> =
            map {
                it.copy(isSelected = !all { note -> note.isSelected })
            }

        private fun List<SelectableNote>.toggleAllSelection(isSelected: Boolean): List<SelectableNote> =
            map {
                it.copy(isSelected = isSelected)
            }
    }
