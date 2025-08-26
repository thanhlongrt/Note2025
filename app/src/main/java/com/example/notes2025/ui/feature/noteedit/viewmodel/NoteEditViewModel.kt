package com.example.notes2025.ui.feature.noteedit.viewmodel

import androidx.lifecycle.ViewModel
import com.example.notes2025.data.repository.NoteRepository
import com.example.notes2025.ui.feature.noteedit.uimodel.EditableNote
import com.example.notes2025.utils.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class NoteEditViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
) : ViewModel() {

    private val _currentNote = MutableStateFlow(EditableNote())
    val currentNote = _currentNote.asStateFlow()

    suspend fun fetchNote(noteId: Int?) {
        if (noteId == null) return
        noteRepository.getNoteById(noteId)?.let {
            _currentNote.value = EditableNote(it)
        }
    }

    fun updateTitle(newTitle: String) {
        _currentNote.value =
            _currentNote.value.copy(
                title = newTitle,
                lastEdit = DateUtils.currentTimeStamp(),
            )
    }

    fun updateContents(newContents: String) {
        _currentNote.value =
            _currentNote.value.copy(
                contents = newContents,
                lastEdit = DateUtils.currentTimeStamp(),
            )
    }
}
