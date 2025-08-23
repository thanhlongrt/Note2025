package com.example.notes2025.ui.feature.notelist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes2025.data.DummyDataProvider
import com.example.notes2025.ui.feature.notelist.uimodel.SelectableNote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
        // TODO: Add dependencies here
) : ViewModel() {
    private val _uiState = MutableStateFlow<NoteListUiState>(NoteListUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        fetchNotes()
    }

    fun fetchNotes() {
        viewModelScope.launch {
            delay(2000) // pretend loading for 2 seconds
            _uiState.value = NoteListUiState.Notes(notes = DummyDataProvider.dummyNotes())
        }
    }

    fun onNoteClick(note: SelectableNote) {
        // TODO: Add logic here
    }

    fun onNoteLongClick(note: SelectableNote) {
        // TODO: Add logic here
    }
}
