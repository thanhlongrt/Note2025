package com.example.notes2025.ui.feature.noteedit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.notes2025.ui.component.NotesTopAppBar
import com.example.notes2025.ui.feature.noteedit.uimodel.EditableNote
import com.example.notes2025.ui.feature.noteedit.viewmodel.NoteEditViewModel
import com.example.notes2025.ui.feature.notelist.viewmodel.NoteListViewModel
import com.example.notes2025.utils.Logger

@Composable
fun NoteEditRoute(
    modifier: Modifier = Modifier,
    navController: NavController,
    noteId: Int? = null,
    viewModel: NoteEditViewModel = hiltViewModel(),
) {
    Logger.debug("opening NoteEditRoute: noteId: $noteId")
    val currentNote by viewModel.currentNote.collectAsStateWithLifecycle()

    val noteListViewModel =
        hiltViewModel<NoteListViewModel>(LocalContext.current as ViewModelStoreOwner)
    Logger.debug("NoteEditRoute composition ${noteListViewModel.hashCode()}")
    LaunchedEffect(noteId) {
        viewModel.fetchNote(noteId)
    }

    NoteEditScreen(
        modifier = modifier,
        note = currentNote,
        onTitleChange = viewModel::updateTitle,
        onContentsChange = viewModel::updateContents,
        popBackStack = {
            navController.popBackStack()
        },
        saveNote = {
            navController.popBackStack()
            noteListViewModel.onNoteAddedOrUpdated(
                viewModel.currentNote.value,
            )
        },
    )
}

@Composable
fun NoteEditScreen(
    modifier: Modifier = Modifier,
    note: EditableNote? = null,
    onTitleChange: (String) -> Unit = {},
    onContentsChange: (String) -> Unit = {},
    popBackStack: () -> Unit = {},
    saveNote: () -> Unit = {},
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .imePadding(),
    ) {
        NotesTopAppBar(
            startContent = {
                Spacer(modifier = Modifier.size(10.dp))
                Box(
                    modifier =
                        Modifier
                            .size(48.dp)
                            .clickable(true) {
                                keyboardController?.hide()
                                popBackStack()
                            },
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                    )
                }
            },
            endContent = {
                Box(
                    modifier =
                        Modifier
                            .size(48.dp)
                            .clickable(true) {
                                keyboardController?.hide()
                                saveNote()
                            },
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Save",
                        tint = MaterialTheme.colorScheme.primary,
                    )
                }
                Spacer(modifier = Modifier.size(10.dp))
            },
        )
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 0.5.dp,
            color = Color.Gray,
        )
        TextField(
            modifier =
                Modifier
                    .fillMaxWidth(),
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
            modifier =
                Modifier
                    .fillMaxSize()
                    .focusRequester(focusRequester),
            textStyle =
                TextStyle(
                    fontSize = 16.sp,
                ),
            value =
                TextFieldValue(
                    text = note?.contents.orEmpty(),
                    selection = TextRange(note?.contents.orEmpty().length),
                ),
            placeholder = {
                Text(text = "Contents")
            },
            onValueChange = { newValue ->
                onContentsChange(newValue.text)
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
        Spacer(Modifier.weight(1f))
    }
}

@Preview(showBackground = true)
@Composable
fun NoteEditScreenPreview() {
    val note =
        EditableNote(
            title = "sample title",
            contents = "sample contents",
        )
    NoteEditScreen(
        note = note,
        onTitleChange = {},
        onContentsChange = {},
    )
}
