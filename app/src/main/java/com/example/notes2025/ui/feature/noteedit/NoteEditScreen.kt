package com.example.notes2025.ui.feature.noteedit

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.notes2025.R
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
    LaunchedEffect(noteId) {
        viewModel.fetchNote(noteId)
    }

    NoteEditScreen(
        modifier = modifier,
        note = currentNote,
        onTitleChange = viewModel::updateTitle,
        onContentsChange = viewModel::updateContents,
        saveNote = {
            navController.popBackStack()
            noteListViewModel.addOrUpdateNote(
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
    saveNote: () -> Unit = {},
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .imePadding()
    ) {
        NotesTopAppBar(
            startContent = {
                Spacer(modifier = Modifier.size(24.dp))
                Text(
                    text = if (note?.id == null) "Add" else "Edit",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                )
            },
            endContent = {
                Box(
                    modifier =
                        Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .clickable(true) {
                                keyboardController?.hide()
                                saveNote()
                            },
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Save",
                        tint = colorResource(R.color.color_FF6B4EFF),
                    )
                }
                Spacer(modifier = Modifier.size(10.dp))
            },
        )

        BasicTextField(
            modifier = Modifier
                .fillMaxWidth(),
            maxLines = 2,
            textStyle =
                TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                ),
            value = note?.title.orEmpty(),
            onValueChange = { newValue ->
                onTitleChange(newValue)
            },
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp)
                        .padding(bottom = 4.dp),
                    contentAlignment = Alignment.TopStart,
                ) {
                    if (note?.title.isNullOrEmpty()) {
                        Text(
                            text = stringResource(R.string.hint_title),
                            color = Color.Gray
                        )

                    }
                    innerTextField()
                }
            },
        )

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .padding(horizontal = 16.dp),
            thickness = 0.5.dp,
            color = Color.LightGray,
        )

        BasicTextField(
            modifier =
                Modifier
                    .fillMaxSize(),
            textStyle =
                TextStyle(
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                ),
            value = note?.contents.orEmpty(),
            onValueChange = { newValue ->
                onContentsChange(newValue)
            },
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .padding(horizontal = 16.dp)
                        .padding(top = 12.dp),
                    contentAlignment = Alignment.TopStart,
                ) {
                    if (note?.contents.isNullOrEmpty()) {
                        Text(
                            text = stringResource(R.string.hint_enter_your_contents),
                            color = Color.Gray
                        )

                    }
                    innerTextField()
                }
            },
        )
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
//        contentsState = TextFieldState()
    )
}
