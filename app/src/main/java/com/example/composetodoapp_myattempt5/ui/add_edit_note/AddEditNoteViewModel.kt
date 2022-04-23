package com.example.composetodoapp_myattempt5.ui.add_edit_note

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetodoapp_myattempt5.data.Note
import com.example.composetodoapp_myattempt5.data.NoteRepository
import com.example.composetodoapp_myattempt5.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val repository: NoteRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    var note by mutableStateOf<Note?>(null)
        private set

    var noteTitle by mutableStateOf("")
        private set

    var noteText by mutableStateOf("")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val noteId = savedStateHandle.get<Int>("noteId")!!
        if (noteId != -1) {
            viewModelScope.launch {
                repository.getNoteById(noteId)?.let { note ->
                    noteTitle = note.title
                    noteText = note.text ?: ""
                    this@AddEditNoteViewModel.note = note
                }
            }
        }
    }

    fun onEvent(event: AddEditNoteEvent) {
        when(event) {
            is AddEditNoteEvent.OnTitleChange -> {
                noteTitle = event.title
            }
            is AddEditNoteEvent.OnTextChange -> {
                noteText = event.text
            }
            is AddEditNoteEvent.OnSaveNoteClick -> {
                viewModelScope.launch {
                    if (noteTitle.isBlank()) {
                        sendUiEvent(UiEvent.OnShowSnackbar(
                            message = "Title cannot be empty"
                        ))
                        return@launch
                    }
                    repository.addNote(
                        Note(
                            title = noteTitle,
                            text = noteText,
                            isDone = note?.isDone ?: false,
                            id = note?.id
                        )
                    )
                    sendUiEvent(UiEvent.OnPopBackstack)
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}