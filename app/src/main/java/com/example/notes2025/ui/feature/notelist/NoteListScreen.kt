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
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.notes2025.R
import com.example.notes2025.model.Note
import com.example.notes2025.ui.NoteEditDestination
import com.example.notes2025.ui.component.AddNotesFloatingButton
import com.example.notes2025.ui.component.EmptyView
import com.example.notes2025.ui.component.NoteCheckBox
import com.example.notes2025.ui.component.NoteItem
import com.example.notes2025.ui.component.NotesTopAppBar
import com.example.notes2025.ui.component.SelectionPanel
import com.example.notes2025.ui.feature.notelist.uimodel.SelectableNote
import com.example.notes2025.ui.feature.notelist.viewmodel.NoteListViewModel
import com.example.notes2025.ui.feature.notelist.viewmodel.SelectionState
import com.example.notes2025.utils.DummyDataProvider
import com.example.notes2025.utils.Logger

@Composable
fun NoteListRoute(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: NoteListViewModel = hiltViewModel(LocalContext.current as ViewModelStoreOwner),
) {
    val notes: List<SelectableNote> by viewModel.notes.collectAsStateWithLifecycle()
    val selectedCount by viewModel.selectedCount.collectAsStateWithLifecycle()
    val allSelected by viewModel.allSelected.collectAsStateWithLifecycle()
    val selectionState by viewModel.selectionState.collectAsStateWithLifecycle()
    val isLoadingMore by viewModel.isLoadingMore.collectAsStateWithLifecycle()
    val shouldShowConfirmationDialog by viewModel.shouldShowConfirmationDialog.collectAsStateWithLifecycle()
    val shouldScrollToTop by viewModel.shouldScrollToTop.collectAsStateWithLifecycle()
    val reachedEnd by viewModel.reachedEnd.collectAsStateWithLifecycle()
    NoteListScreen(
        modifier = modifier,
        notes = notes,
        selectedCount = selectedCount,
        allSelected = allSelected,
        selectionState = selectionState,
        isLoadingMore = isLoadingMore,
        shouldShowConfirmationDialog = shouldShowConfirmationDialog,
        shouldScrollToTop = shouldScrollToTop,
        reachedEnd = reachedEnd,
        clearSelection = viewModel::clearSelection,
        toggleAllSelection = viewModel::toggleAllSelection,
        onNoteSelected = viewModel::onNoteSelected,
        onNoteLongClick = viewModel::onNoteLongClick,
        showConfirmationDialog = viewModel::showConfirmationDialog,
        deleteSelectedNotes = viewModel::deleteSelectedNotes,
        hideConfirmationDialog = viewModel::hideConfirmationDialog,
        resetScrollToTopState = viewModel::resetScrollToTopState,
        openNoteEditScreen = { note ->
            val route =
                if (note == null) {
                    NoteEditDestination.route
                } else {
                    "${NoteEditDestination.route}/${note.id}"
                }
            navController.navigate(route)
        },
        addDummyData = viewModel::addDummyData, // for development
        loadMoreNotes = viewModel::loadMoreNotes,
    )
}

@Composable
fun NoteListScreen(
    modifier: Modifier = Modifier,
    notes: List<SelectableNote> = listOf(),
    selectedCount: Int = 0,
    allSelected: Boolean = false,
    selectionState: SelectionState = SelectionState.Off,
    isLoadingMore: Boolean = false,
    shouldShowConfirmationDialog: Boolean = false,
    shouldScrollToTop: Boolean = false,
    reachedEnd: Boolean = false,
    clearSelection: () -> Unit = {},
    toggleAllSelection: () -> Unit = {},
    onNoteSelected: (SelectableNote) -> Unit = {},
    onNoteLongClick: (SelectableNote) -> Unit = {},
    showConfirmationDialog: () -> Unit = {},
    deleteSelectedNotes: () -> Unit = {},
    hideConfirmationDialog: () -> Unit = {},
    openNoteEditScreen: (Note?) -> Unit = {},
    addDummyData: () -> Unit = {},
    loadMoreNotes: () -> Unit = {},
    resetScrollToTopState: () -> Unit = {},
) {
    val lazyGridState = rememberLazyGridState()
    val selectionEnabled = selectionState == SelectionState.On
    LaunchedEffect(shouldScrollToTop) {
        if (shouldScrollToTop) {
            resetScrollToTopState()
            lazyGridState.scrollToItem(0)
        }
    }
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
                    Spacer(modifier = Modifier.size(24.dp))
                    AppBarStartContent(
                        selectionEnabled = selectionEnabled,
                        allSelected = allSelected,
                        toggleAllSelection = toggleAllSelection,
                        selectedCount = selectedCount,
                    )
                },
                endContent = {
                    // For development
//                    AppBarEndContent(addDummyData)
                    Spacer(modifier = Modifier.size(24.dp))
                },
            )

            if (reachedEnd && notes.isEmpty()) {
                EmptyView(modifier = Modifier.fillMaxWidth().weight(1f))
            } else {
                NoteList(
                    notes = notes,
                    isSelectionEnabled = selectionEnabled,
                    isLoadingMore = isLoadingMore,
                    reachedEnd = reachedEnd,
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
                    loadMoreNotes = loadMoreNotes,
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
                        ambientColor = colorResource(R.color.color_FF6B4EFF),
                        spotColor = colorResource(R.color.color_FF6B4EFF),
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

    if (shouldShowConfirmationDialog) {
        AlertDialog(
            title = { Text(text = stringResource(R.string.title_delete_notes)) },
            text = { Text(text = stringResource(R.string.message_delete_notes_confirmation)) },
            confirmButton = {
                TextButton(
                    onClick = {
                        deleteSelectedNotes()
                        hideConfirmationDialog()
                    },
                ) { Text(text = stringResource(R.string.button_delete)) }
            },
            dismissButton = {
                TextButton(onClick = hideConfirmationDialog) { Text(text = stringResource(R.string.button_cancel)) }
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
        NoteCheckBox(
            modifier =
                Modifier
                    .padding(end = 18.dp)
                    .size(24.dp),
            checked = allSelected,
            onCheckedChange = {
                toggleAllSelection()
            },
        )
    }
    if (selectionEnabled) {
        Text(
            text = stringResource(R.string.title_selected, selectedCount),
            fontSize = 22.sp,
        )
    } else {
        Text(
            text = stringResource(R.string.title_notes),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun NoteList(
    modifier: Modifier = Modifier,
    notes: List<SelectableNote>,
    isSelectionEnabled: Boolean = false,
    isLoadingMore: Boolean = false,
    reachedEnd: Boolean = false,
    columnCount: Int = 2,
    onNoteClick: (SelectableNote) -> Unit = {},
    onNoteLongClick: (SelectableNote) -> Unit = {},
    loadMoreNotes: () -> Unit = {},
    lazyGridState: LazyGridState = rememberLazyGridState(),
) {
    val threshold = 4
    val nearEnd =
        remember {
            derivedStateOf {
                val lastVisibleItemIndex =
                    lazyGridState.layoutInfo.visibleItemsInfo
                        .lastOrNull()
                        ?.index
                val totalItemsCount = lazyGridState.layoutInfo.totalItemsCount
                lastVisibleItemIndex != null && (lastVisibleItemIndex >= totalItemsCount - threshold)
            }
        }.value

    LaunchedEffect(nearEnd) {
        if (nearEnd && !isLoadingMore) {
            loadMoreNotes()
        }
    }

    val isScrollingDown = lazyGridState.isScrollingDown()
    LaunchedEffect(isScrollingDown) {
        if (isScrollingDown) {
            Logger.debug("Scrolling down")
        }
    }
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
            key = { index -> notes[index].id ?: notes[index].lastEdit },
        ) { index ->
            val note = notes[index]
            NoteItem(
                modifier =
                    Modifier
                        .height(280.dp),
                title = note.title,
                lastEdit = note.lastEditStr,
                contents = note.contents,
                isSelectionEnabled = isSelectionEnabled,
                isSelected = note.isSelected,
                onNoteClick = { onNoteClick(note) },
                onNoteLongClick = { onNoteLongClick(note) },
            )
        }
        item(span = { GridItemSpan(maxLineSpan) }) {
            AnimatedVisibility(
                visible = reachedEnd && isScrollingDown,
                enter = fadeIn(),
                exit = fadeOut(),
            ) {
                Text(
                    text = stringResource(R.string.message_no_more_notes),
                    color = Color.Gray, textAlign = TextAlign.Center
                )
            }
        }

        if (isLoadingMore) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Box(
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.fillMaxHeight()
                    )
                }

            }
        }

        // Space for FAB
        item(span = { GridItemSpan(maxLineSpan) }) {
            val paddingBottom = if (isLoadingMore) {
                if (isSelectionEnabled) {
                    56.dp
                } else {
                    0.dp
                }
            } else {
                96.dp
            }
            Spacer(
                modifier = Modifier.height(
                    paddingBottom
                )
            )
        }
    }
}

@Composable
private fun LazyGridState.isScrollingDown(): Boolean {
    var previousIndex by remember(this) { mutableIntStateOf(firstVisibleItemIndex) }
    var previousScrollOffset by remember(this) { mutableIntStateOf(firstVisibleItemScrollOffset) }
    return remember(this) {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex <= firstVisibleItemIndex
            } else {
                previousScrollOffset < firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
}

@Preview(showBackground = true)
@Composable
fun NoteListPreview() {
    val notes = DummyDataProvider.dummyNotes()
    NoteListScreen(
        notes = notes,
    )
}
