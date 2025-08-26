package com.example.notes2025.ui.feature.notelist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes2025.data.repository.NoteRepository
import com.example.notes2025.ui.feature.noteedit.uimodel.EditableNote
import com.example.notes2025.ui.feature.notelist.uimodel.SelectableNote
import com.example.notes2025.utils.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
) : ViewModel() {
    private val _notes = MutableStateFlow(listOf<SelectableNote>())
    val notes = _notes.asStateFlow()

    private val _selectedCount = MutableStateFlow(0)
    val selectedCount = _selectedCount.asStateFlow()

    private val _allSelected = MutableStateFlow(false)
    val allSelected = _allSelected.asStateFlow()

    private val _selectionState = MutableStateFlow(SelectionState.Off)
    val selectionState = _selectionState.asStateFlow()

    private val _isLoadingMore = MutableStateFlow(false)
    val isLoadingMore = _isLoadingMore.asStateFlow()

    private val _shouldShowConfirmationDialog = MutableStateFlow(false)
    val shouldShowConfirmationDialog = _shouldShowConfirmationDialog.asStateFlow()

    private val _shouldScrollToTop = MutableStateFlow(false)
    val shouldScrollToTop = _shouldScrollToTop.asStateFlow()

    init {
//            fetchNotes()
        loadMoreNotes()
    }

    private var nextPage = 0
    private val pageSize = 20
    private var reachedEnd = false
    private var additionalFetchOffset =
        0 // after adding/removing items, we need to update the offset

    fun loadMoreNotes() {
        if (_isLoadingMore.value || _selectionState.value == SelectionState.On) return // if already loading, return
        _isLoadingMore.value = true // mark as loading
        viewModelScope.launch(Dispatchers.IO) {
            if (nextPage == 0) {
                additionalFetchOffset = 0 // if this is the first page, no need to add offset
            }
            nextPage =
                nextPage + additionalFetchOffset / pageSize // excess page size, update page number
            additionalFetchOffset =
                additionalFetchOffset % pageSize // remaining offset after page number  is updated
            Logger.debug("fetchPagedNotes: limit = $pageSize, additionalFetchOffset = $additionalFetchOffset ")
            val newPage =
                noteRepository
                    .fetchPagedNotes(
                        pageSize = pageSize,
                        page = nextPage,
                        additionalFetchOffset = additionalFetchOffset,
                    ).map(::SelectableNote)
            if (newPage.size < pageSize) reachedEnd = true
            val newNotes = _notes.value.toMutableList().apply { addAll(newPage) }
            updateNoteState(newNotes)
            _isLoadingMore.value = false
            nextPage++
        }
    }

    // Observe changes from DB
    fun fetchNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository
                .getNoteStream()
                .onEach { notes ->
                    Logger.debug("fetchNotes: onEach \n $notes")
                }.collect { notes ->
                    val newNotes = notes.map(::SelectableNote)
                    updateNoteState(newNotes)
                }
        }
    }

    fun onNoteSelected(note: SelectableNote) {
        if (_selectionState.value != SelectionState.On) return
        updateNoteState(_notes.value.toggleSelection(note))
    }

    fun onNoteLongClick(note: SelectableNote) {
        if (_selectionState.value == SelectionState.On) return
        _selectionState.value = SelectionState.On
        updateNoteState(_notes.value.toggleSelection(note))
    }

    fun clearSelection() {
        _selectionState.value = SelectionState.Off
        updateNoteState(_notes.value.toggleAllSelection(isSelected = false))
    }

    fun toggleAllSelection() {
        updateNoteState(_notes.value.toggleAllSelection())
    }

    fun deleteSelectedNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            val notesToDelete =
                _notes.value
                    .filter(SelectableNote::isSelected)
                    .mapNotNull(SelectableNote::id)
            clearSelection()
            noteRepository.deleteNotes(notesToDelete)
            val updatedList =
                _notes.value.toMutableList().apply { removeAll { it.id in notesToDelete } }
            updateNoteState(updatedList)
            additionalFetchOffset = additionalFetchOffset - notesToDelete.size
        }
    }

    // for developers
    fun addDummyData() {
        viewModelScope.launch(Dispatchers.IO) {
            (1..200).reversed().map {
                noteRepository.insertNote(
                    SelectableNote(
                        title = "Note $it",
                        contents = "Content $it",
                    ).toNote().also {
                        Logger.debug("Insert: $it")
                    }
                )
                delay(200) // for distinct time stamps
            }
        }
    }

    fun showConfirmationDialog() {
        _shouldShowConfirmationDialog.value = true
    }

    fun hideConfirmationDialog() {
        _shouldShowConfirmationDialog.value = false
    }

    fun onNoteAddedOrUpdated(newNote: EditableNote) {
        newNote.id?.let { id ->
            // if new note is updated
            viewModelScope.launch {
                val updatedNote = newNote.toNote()
                noteRepository.saveNote(updatedNote) // save item to the database
                Logger.debug("Update: $updatedNote")
                // remove the old item and add the newly updated item to the top
                val updatedList =
                    _notes.value.toMutableList().apply {
                        removeIf { it.id == id }
                        add(0, SelectableNote(updatedNote))
                    }
                updateNoteState(updatedList)
                _shouldScrollToTop.value = true
            }
        } ?: run {
            // if new note is added
            viewModelScope.launch {
                // insert into database to get the id
                val id = noteRepository.insertNote(
                    newNote.toNote().also { Logger.debug("Insert: $it") }
                )
                val note =
                    noteRepository.getNoteById(id.toInt()) // get the inserted item from the database
                additionalFetchOffset = additionalFetchOffset + 1
                Logger.debug("A new note is added, increase additionalFetchOffset value by 1 -> $additionalFetchOffset")
                // add the newly added item to the top
                val updatedList =
                    _notes.value.toMutableList().apply {
                        note?.let { add(0, SelectableNote(it)) }
                    }
                updateNoteState(updatedList)
                _shouldScrollToTop.value = true
            }
        }
    }

    fun resetScrollToTopState() {
        _shouldScrollToTop.value = false
    }

    private fun updateNoteState(newState: List<SelectableNote>) {
        _notes.value = newState
        _selectedCount.value = _notes.value.count { it.isSelected }
        _allSelected.value = _notes.value.all { it.isSelected }
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
        map { it.copy(isSelected = !all { note -> note.isSelected }) }

    private fun List<SelectableNote>.toggleAllSelection(isSelected: Boolean): List<SelectableNote> =
        map { it.copy(isSelected = isSelected) }
}
