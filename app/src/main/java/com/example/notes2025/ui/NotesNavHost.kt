package com.example.notes2025.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notes2025.ui.feature.noteedit.NoteEditRoute
import com.example.notes2025.ui.feature.notelist.NoteListRoute

@Composable
fun NotesNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = "note_list",
        modifier = modifier,
    ) {
        composable(route = NoteListDestination.route) {
            NoteListRoute(
                openNoteEditScreen = { note ->
                    val route =
                        if (note == null) {
                            NoteEditDestination.route
                        } else {
                            "${NoteEditDestination.route}/${note.id}"
                        }
                    navController.navigate(route)
                },
            )
        }
        composable(
            route = NoteEditDestination.route,
        ) { navBackStackEntry ->
            NoteEditRoute(
                noteId = null,
            )
        }
        composable(
            route = NoteEditDestination.routeWithArgs,
            arguments = NoteEditDestination.arguments,
        ) { navBackStackEntry ->
            val noteId =
                navBackStackEntry.arguments?.getString(NoteEditDestination.NOTE_ID_ARG)
            NoteEditRoute(
                noteId = noteId,
            )
        }
    }
}
