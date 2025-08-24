package com.example.notes2025.ui.feature.noteedit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes2025.data.repository.NoteRepository
import com.example.notes2025.utils.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteEditViewModel
    @Inject
    constructor(
        private val noteRepository: NoteRepository,
    ) : ViewModel() {
        init {
            Logger.debug("init $this")
        }

        private val _uiState = MutableStateFlow(NoteEditUiState())
        val uiState = _uiState.asStateFlow()
        private val currentState get() = _uiState.value

        suspend fun fetchNote(noteId: String?) {
            if (noteId.isNullOrBlank()) return
            val note = noteRepository.getNoteById(noteId)
            _uiState.value = currentState.copy(currentNote = note)
        }

        fun saveCurrentNote() {
            val currentNote = currentState.currentNote ?: return
            viewModelScope.launch {
                noteRepository.saveNotes(listOf(currentNote))
            }
        }

        fun updateTitle(newTitle: String) {
            _uiState.value =
                currentState.copy(currentNote = currentState.currentNote?.copy(title = newTitle))
        }

        fun updateContents(newContents: String) {
            _uiState.value =
                currentState.copy(currentNote = currentState.currentNote?.copy(contents = newContents))
        }
    }
