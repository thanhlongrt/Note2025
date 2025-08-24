package com.example.notes2025.ui.feature.notelist

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.notes2025.utils.DummyDataProvider
import com.example.notes2025.ui.feature.notelist.component.AddNotesFloatingButton
import com.example.notes2025.ui.feature.notelist.component.NoteItem
import com.example.notes2025.ui.feature.notelist.component.NotesTopAppBar
import com.example.notes2025.ui.feature.notelist.component.SelectionPanel
import com.example.notes2025.ui.feature.notelist.uimodel.SelectableNote
import com.example.notes2025.ui.feature.notelist.viewmodel.NoteListUiState
import com.example.notes2025.ui.feature.notelist.viewmodel.NoteListViewModel
import com.example.notes2025.ui.feature.notelist.viewmodel.SelectionState

@Composable
fun NoteListScreen(
    modifier: Modifier = Modifier,
    viewModel: NoteListViewModel = hiltViewModel(),
) {
    val uiState: NoteListUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lazyGridState = rememberLazyGridState()
    val selectedCount = uiState.selectedCount
    val allSelected = uiState.allSelected
    BackHandler(
        enabled = uiState.selectionState == SelectionState.On,
        onBack = {
            viewModel.clearSelection()
        },
    )
    Box {
        val selectionEnabled = uiState.selectionState == SelectionState.On
        Column(modifier = modifier) {
            NotesTopAppBar(
                isSelectionEnabled = selectionEnabled,
                allSelected = allSelected,
                selectedCount = selectedCount,
                onCheckedChange = {
                    viewModel.toggleAllSelection()
                },
            )
            if (uiState.isLoading) {
                // TODO: show loading
            } else {
                NoteList(
                    notes = uiState.notes,
                    isSelectionEnabled = selectionEnabled,
                    onNoteClick = { note ->
                        viewModel.onNoteClick(note)
                    },
                    onNoteLongClick = { note ->
                        viewModel.onNoteLongClick(note)
                    },
                    lazyGridState = lazyGridState,
                )
            }
        }

        val isExpanded by remember {
            derivedStateOf { lazyGridState.firstVisibleItemIndex == 0 }
        }
        AddNotesFloatingButton(
            isSelectionEnabled = selectionEnabled,
            isExpanded = isExpanded,
            modifier = modifier.align(Alignment.BottomEnd),
        )

        SelectionPanel(
            modifier = modifier.align(Alignment.BottomCenter),
            visible = selectionEnabled && selectedCount > 0,
            onClick = {
                viewModel.deleteSelectedNotes()
            },
        )
    }
}

@Composable
fun NoteList(
    modifier: Modifier = Modifier,
    notes: List<SelectableNote>,
    isSelectionEnabled: Boolean = false,
    onNoteClick: (SelectableNote) -> Unit = {},
    onNoteLongClick: (SelectableNote) -> Unit = {},
    lazyGridState: LazyGridState = rememberLazyGridState(),
) {
    LazyVerticalGrid(
        modifier = modifier.fillMaxHeight(),
        columns = GridCells.Fixed(2),
        state = lazyGridState,
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(
            count = notes.size,
            key = { index -> notes[index].id },
        ) { index ->
            val note = notes[index]
            NoteItem(
                title = note.title,
                lastEdit = note.lastEditStr,
                contents = note.contents,
                modifier = Modifier.height(300.dp),
                isSelectionEnabled = isSelectionEnabled,
                isSelected = note.isSelected,
                onNoteClick = { onNoteClick(note) },
                onNoteLongClick = { onNoteLongClick(note) },
            )
        }
        item { Spacer(modifier = Modifier.height(96.dp)) }
    }
}

@Preview(showBackground = true)
@Composable
fun NoteListPreview() {
    val notes = DummyDataProvider.dummyNotes()
    NoteList(
        notes = notes,
        onNoteClick = {},
    )
}
