package com.example.composetodoapp_myattempt5.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.composetodoapp_myattempt5.ui.add_edit_note.AddEditNoteScreen
import com.example.composetodoapp_myattempt5.ui.note_list.NoteListScreen

@Composable
fun SetUpGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.NOTE_LIST
    ) {
        composable(
            Routes.NOTE_LIST
        ) {
            NoteListScreen(onNavigate = {
                navController.navigate(it.route)
            })
        }
        composable(
            route = Routes.ADD_EDIT_NOTE + "?noteId={noteId}",
            arguments = listOf(
                navArgument(name = "noteId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {
            AddEditNoteScreen(onPopBackstack = {
                navController.popBackStack()
            })
        }
    }
}