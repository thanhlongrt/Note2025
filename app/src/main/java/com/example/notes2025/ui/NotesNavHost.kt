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
                navController = navController,
            )
        }
        composable(
            route = NoteEditDestination.route,
        ) { navBackStackEntry ->
            NoteEditRoute(
                navController = navController,
                noteId = null,
            )
        }
        composable(
            route = NoteEditDestination.routeWithArgs,
            arguments = NoteEditDestination.arguments,
        ) { navBackStackEntry ->
            val noteId =
                navBackStackEntry.arguments?.getInt(NoteEditDestination.NOTE_ID_ARG)
            NoteEditRoute(
                navController = navController,
                noteId = noteId,
            )
        }
    }
}
