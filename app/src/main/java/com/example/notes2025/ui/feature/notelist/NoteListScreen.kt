package com.example.notes2025.ui.feature.notelist

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.notes2025.model.Note
import com.example.notes2025.ui.component.AddNotesFloatingButton
import com.example.notes2025.ui.component.CustomCheckBox
import com.example.notes2025.ui.component.NoteItem
import com.example.notes2025.ui.component.NotesTopAppBar
import com.example.notes2025.ui.component.SelectionPanel
import com.example.notes2025.ui.feature.notelist.uimodel.SelectableNote
import com.example.notes2025.ui.feature.notelist.viewmodel.NoteListUiState
import com.example.notes2025.ui.feature.notelist.viewmodel.NoteListViewModel
import com.example.notes2025.ui.feature.notelist.viewmodel.SelectionState
import com.example.notes2025.utils.DummyDataProvider

@Composable
fun NoteListRoute(
    modifier: Modifier = Modifier,
    viewModel: NoteListViewModel = hiltViewModel(),
    openNoteEditScreen: (Note?) -> Unit = {},
) {
    val uiState: NoteListUiState by viewModel.uiState.collectAsStateWithLifecycle()
    NoteListScreen(
        modifier = modifier,
        uiState = uiState,
        clearSelection = viewModel::clearSelection,
        toggleAllSelection = viewModel::toggleAllSelection,
        onNoteSelected = viewModel::onNoteSelected,
        onNoteLongClick = viewModel::onNoteLongClick,
        showConfirmationDialog = viewModel::showConfirmationDialog,
        deleteSelectedNotes = viewModel::deleteSelectedNotes,
        hideConfirmationDialog = viewModel::hideConfirmationDialog,
        openNoteEditScreen = openNoteEditScreen,
        addDummyData = viewModel::addDummyData,
    )
}

@Composable
fun NoteListScreen(
    modifier: Modifier = Modifier,
    uiState: NoteListUiState,
    clearSelection: () -> Unit = {},
    toggleAllSelection: () -> Unit = {},
    onNoteSelected: (SelectableNote) -> Unit = {},
    onNoteLongClick: (SelectableNote) -> Unit = {},
    showConfirmationDialog: () -> Unit = {},
    deleteSelectedNotes: () -> Unit = {},
    hideConfirmationDialog: () -> Unit = {},
    openNoteEditScreen: (Note?) -> Unit = {},
    addDummyData: () -> Unit = {},
) {
    val lazyGridState = rememberLazyGridState()
    val selectedCount = uiState.selectedCount
    val allSelected = uiState.allSelected
    val selectionEnabled = uiState.selectionState == SelectionState.On
    BackHandler(
        enabled = selectionEnabled,
        onBack = {
            clearSelection()
        },
    )
    Box(modifier = modifier) {
        Column(modifier = Modifier.fillMaxSize()) {
            NotesTopAppBar(
                startContent = {
                    AppBarStartContent(
                        selectionEnabled = selectionEnabled,
                        allSelected = allSelected,
                        toggleAllSelection = toggleAllSelection,
                        selectedCount = selectedCount,
                    )
                },
                endContent = {
                    AppBarEndContent(addDummyData)
                },
            )
            if (uiState.isLoading) {
                // TODO: show loading
            } else {
                NoteList(
                    notes = uiState.notes,
                    isSelectionEnabled = selectionEnabled,
                    onNoteClick = { note ->
                        if (selectionEnabled) {
                            onNoteSelected(note)
                        } else {
                            openNoteEditScreen(note.toNote())
                        }
                    },
                    onNoteLongClick = { note ->
                        onNoteLongClick(note)
                    },
                    lazyGridState = lazyGridState,
                )
            }
        }

        val isExpanded by remember {
            derivedStateOf { lazyGridState.firstVisibleItemIndex == 0 }
        }
        AnimatedVisibility(
            modifier =
                Modifier
                    .align(Alignment.BottomEnd)
                    .padding(
                        bottom = 56.dp,
                        end = 16.dp,
                    )
                    // Show shadow right after it's visible
                    .shadow(
                        elevation = 20.dp,
                        shape = RoundedCornerShape(16.dp),
                        ambientColor = Color(0xFF6B4EFF),
                        spotColor = Color(0xFF6B4EFF),
                    ),
            visible = !selectionEnabled,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            AddNotesFloatingButton(
                isExpanded = isExpanded,
                onClick = { openNoteEditScreen(null) },
            )
        }

        AnimatedVisibility(
            modifier =
                Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(56.dp),
            visible = selectionEnabled && selectedCount > 0,
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOutVertically(targetOffsetY = { it }),
        ) {
            SelectionPanel(
                onClick = showConfirmationDialog,
            )
        }
    }

    if (uiState.showConfirmationDialog) {
        AlertDialog(
            title = { Text(text = "Delete Notes") },
            text = { Text(text = "Are you sure you want to delete all selected items?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        deleteSelectedNotes()
                        hideConfirmationDialog()
                    },
                ) { Text(text = "Delete") }
            },
            dismissButton = {
                TextButton(onClick = hideConfirmationDialog) { Text(text = "Cancel") }
            },
            onDismissRequest = hideConfirmationDialog,
        )
    }
}

@Composable
private fun AppBarEndContent(addDummyData: () -> Unit) {
    TextButton(
        modifier = Modifier,
        onClick = addDummyData,
    ) {
        Text(text = "Add dummy data")
    }
}

@Composable
private fun AppBarStartContent(
    selectionEnabled: Boolean,
    allSelected: Boolean,
    toggleAllSelection: () -> Unit,
    selectedCount: Int,
) {
    if (selectionEnabled) {
        CustomCheckBox(
            modifier =
                Modifier
                    .padding(end = 16.dp)
                    .size(30.dp),
            checked = allSelected,
            onCheckedChange = {
                toggleAllSelection()
            },
        )
    }
    if (selectionEnabled) {
        Text(
            text = "$selectedCount selected",
            fontSize = 28.sp,
        )
    } else {
        Text(
            text = "Notes",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun NoteList(
    modifier: Modifier = Modifier,
    notes: List<SelectableNote>,
    isSelectionEnabled: Boolean = false,
    columnCount: Int = 2,
    onNoteClick: (SelectableNote) -> Unit = {},
    onNoteLongClick: (SelectableNote) -> Unit = {},
    lazyGridState: LazyGridState = rememberLazyGridState(),
) {
    LazyVerticalGrid(
        modifier = modifier.fillMaxHeight(),
        columns = GridCells.Fixed(columnCount),
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
                modifier =
                    Modifier
                        .height(280.dp),
                isSelectionEnabled = isSelectionEnabled,
                isSelected = note.isSelected,
                onNoteClick = { onNoteClick(note) },
                onNoteLongClick = { onNoteLongClick(note) },
            )
        }

        // Space for FAB
        repeat(columnCount) {
            item {
                Spacer(modifier = Modifier.height(96.dp))
            }
        }
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
