package com.example.notes2025.ui.feature.noteedit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.notes2025.model.Note
import com.example.notes2025.ui.feature.notelist.component.NotesTopAppBar
import com.example.notes2025.utils.Logger

@Composable
fun NoteEditRoute(
    modifier: Modifier = Modifier,
    noteId: String? = null,
) {
    Logger.debug("opening NoteEditRoute: noteId: $noteId")
    val note: Note? = null
    NoteEditScreen(
        modifier = modifier,
        note = note,
    )
}

@Composable
fun NoteEditScreen(
    modifier: Modifier = Modifier,
    note: Note? = null,
) {
    var title by rememberSaveable { mutableStateOf(note?.title.orEmpty()) }
    var contents by rememberSaveable { mutableStateOf(note?.contents.orEmpty()) }
    Column(modifier = modifier) {
        NotesTopAppBar()
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = title,
            maxLines = 2,
            placeholder = {
                Text(text = "Title")
            },
            onValueChange = { newValue ->
                title = newValue
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
            value = contents,
            placeholder = {
                Text(text = "Contents")
            },
            onValueChange = { newValue ->
                contents = newValue
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
