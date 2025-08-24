package com.example.notes2025.ui.feature.noteedit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.notes2025.model.Note
import com.example.notes2025.ui.component.NotesTopAppBar
import com.example.notes2025.ui.feature.noteedit.viewmodel.NoteEditViewModel
import com.example.notes2025.utils.Logger

@Composable
fun NoteEditRoute(
    modifier: Modifier = Modifier,
    noteId: String? = null,
    viewModel: NoteEditViewModel = hiltViewModel(),
) {
    Logger.debug("opening NoteEditRoute: noteId: $noteId")
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(noteId) {
        viewModel.fetchNote(noteId)
    }

    DisposableEffect(Unit) {
        // This block runs when the Composable enters composition
        Logger.debug("MyScreen entered composition")

        onDispose {
            // This block runs when the Composable leaves composition (e.g., screen navigated away, Composable removed)
            Logger.debug("MyScreen left composition")
            viewModel.saveCurrentNote()
        }
    }

    NoteEditScreen(
        modifier = modifier,
        note = uiState.currentNote,
        onTitleChange = viewModel::updateTitle,
        onContentsChange = viewModel::updateContents,
    )
}

@Composable
fun NoteEditScreen(
    modifier: Modifier = Modifier,
    note: Note? = null,
    onTitleChange: (String) -> Unit = {},
    onContentsChange: (String) -> Unit = {},
) {
    Column(modifier = modifier) {
        NotesTopAppBar() // TODO add contents
        TextField(
            modifier = Modifier.fillMaxWidth(),
            maxLines = 2,
            textStyle =
                TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                ),
            value = note?.title.orEmpty(),
            placeholder = {
                Text(text = "Title")
            },
            onValueChange = { newValue ->
                onTitleChange(newValue)
            },
            colors =
                TextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                ),
        )
        TextField(
            modifier = Modifier.fillMaxSize(),
            textStyle =
                TextStyle(
                    fontSize = 16.sp,
                ),
            value = note?.contents.orEmpty(),
            placeholder = {
                Text(text = "Contents")
            },
            onValueChange = { newValue ->
                onContentsChange(newValue)
            },
            colors =
                TextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                ),
        )
    }
}
