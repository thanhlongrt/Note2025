package com.example.notes2025.ui.feature.notelist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes2025.Logger
import com.example.notes2025.data.repository.NoteRepository
import com.example.notes2025.ui.feature.notelist.uimodel.SelectableNote
import com.example.notes2025.utils.DummyDataProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel
    @Inject
    constructor(
        private val noteRepository: NoteRepository,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(NoteListUiState())
        val uiState = _uiState.asStateFlow()
        private val currentState get() = _uiState.value

        init {
            fetchNotes()
        }

        fun fetchNotes() {
            viewModelScope.launch(Dispatchers.IO) {
                noteRepository
                    .getNotesStream()
                    .onEach { notes ->
                        Logger.debug("fetchNotes: onEach \n $notes")
                    }.collect { notes ->
                        _uiState.value = currentState.copy(notes = notes.map(::SelectableNote))
                    }
            }
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
            viewModelScope.launch(Dispatchers.IO) {
                val notesToDelete =
                    currentState.notes
                        .filter(SelectableNote::isSelected)
                        .map(SelectableNote::id)
                clearSelection()
                noteRepository.deleteNotes(notesToDelete)
            }
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

        fun onFabClick() {
            // TODO: open NoteEditScreen
            viewModelScope.launch(Dispatchers.IO) {
                noteRepository.insertNotes(
                    DummyDataProvider.dummyNotes().map(SelectableNote::toNote),
                )
            }
        }

        fun showConfirmationDialog() {
            _uiState.value = currentState.copy(showConfirmationDialog = true)
        }

        fun hideConfirmationDialog() {
            _uiState.value = currentState.copy(showConfirmationDialog = false)
        }
    }
