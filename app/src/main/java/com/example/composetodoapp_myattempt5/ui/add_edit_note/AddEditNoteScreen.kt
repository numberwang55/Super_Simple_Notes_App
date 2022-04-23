package com.example.composetodoapp_myattempt5.ui.add_edit_note

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composetodoapp_myattempt5.ui.note_list.NoteListEvent
import com.example.composetodoapp_myattempt5.util.UiEvent
import kotlinx.coroutines.flow.collect

@Composable
fun AddEditNoteScreen(
    viewModel: AddEditNoteViewModel = hiltViewModel(),
    onPopBackstack: () -> Unit
) {

    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.OnPopBackstack -> {
                    onPopBackstack()
                }
                is UiEvent.OnShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        event.message,
                        event.action
                    )
                }
                else -> Unit
            }
        }
    }
    Scaffold(topBar = {
        TopAppBar(
            title = { Text(
                text = "Add/Edit note"
            ) },
            navigationIcon = {
                IconButton(onClick = {
                    onPopBackstack()
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
            elevation = 8.dp
        )
    },
        modifier = Modifier
            .fillMaxSize(),
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(AddEditNoteEvent.OnSaveNoteClick)
            }) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Save note"
                )
            }
        }
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = viewModel.noteTitle,
                onValueChange = {
                    viewModel.onEvent(AddEditNoteEvent.OnTitleChange(it))
                },
                placeholder = {
                    Text(text = "Title")
                },
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = viewModel.noteText,
                onValueChange = {
                    viewModel.onEvent(AddEditNoteEvent.OnTextChange(it))
                },
                placeholder = {
                    Text(text = "Text")
                },
                modifier = Modifier
                    .fillMaxWidth(),
                maxLines = 20
            )
        }
    }
}