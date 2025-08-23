package com.example.notes2025.ui.feature.notelist

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.notes2025.data.DummyDataProvider
import com.example.notes2025.ui.feature.notelist.component.NoteItem
import com.example.notes2025.ui.feature.notelist.component.TopAppBar
import com.example.notes2025.ui.feature.notelist.uimodel.SelectableNote
import com.example.notes2025.ui.feature.notelist.viewmodel.NoteListUiState
import com.example.notes2025.ui.feature.notelist.viewmodel.NoteListViewModel

@Composable
fun NoteListScreen(
    modifier: Modifier = Modifier,
    viewModel: NoteListViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val uiState: NoteListUiState by viewModel.uiState.collectAsStateWithLifecycle()
    Column(modifier = modifier) {
        val currentState = uiState
        TopAppBar()
        when (currentState) {
            NoteListUiState.Loading -> {
                // TODO: Show loading
            }
            is NoteListUiState.Notes -> {
                NoteList(
                    notes = currentState.notes,
                    onNoteClick = { note ->
                        Toast
                            .makeText(
                                context,
                                "Clicked on Note ID:${note.id}",
                                Toast.LENGTH_SHORT,
                            ).show()
                        viewModel.onNoteClick(note)
                    },
                    onNoteLongClick = { note ->
                        Toast
                            .makeText(
                                context,
                                "Long Clicked on Note ID:${note.id}",
                                Toast.LENGTH_SHORT,
                            ).show()
                        viewModel.onNoteLongClick(note)
                    },
                )
            }
        }
    }
}

@Composable
fun NoteList(
    modifier: Modifier = Modifier,
    notes: List<SelectableNote>,
    onNoteClick: (SelectableNote) -> Unit,
    onNoteLongClick: (SelectableNote) -> Unit = {},
) {
    LazyVerticalStaggeredGrid(
        modifier = modifier.fillMaxHeight(),
        columns = StaggeredGridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalItemSpacing = 16.dp,
    ) {
        items(
            count = notes.size,
            key = { index -> notes[index].id },
        ) { index ->
            val note = notes[index]
            NoteItem(
                title = note.title,
                contents = note.contents,
                isSelectable = true,
                isSelected = note.isSelected,
                onNoteClick = { onNoteClick(note) },
                onNoteLongClick = { onNoteLongClick(note) },
            )
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
