package com.example.composetodoapp_myattempt5.ui.note_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetodoapp_myattempt5.data.Note
import com.example.composetodoapp_myattempt5.data.NoteRepository
import com.example.composetodoapp_myattempt5.navigation.Routes
import com.example.composetodoapp_myattempt5.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val repository: NoteRepository
): ViewModel() {

    val notes = repository.getNotes()
    var deletedNote by mutableStateOf<Note?>(null)
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: NoteListEvent){
        when(event) {
            is NoteListEvent.OnAddNoteClick -> {
                sendUiEvent(UiEvent.OnNavigate(Routes.ADD_EDIT_NOTE))
            }
            is NoteListEvent.OnDeleteNoteClick -> {
                viewModelScope.launch {
                    deletedNote = event.note
                    repository.deleteNote(event.note)
                    sendUiEvent(UiEvent.OnShowSnackbar(
                        message = "Note deleted",
                        action = "Undo"
                    ))
                }
            }
            is NoteListEvent.OnDoneChange -> {
                viewModelScope.launch {
                    repository.addNote(
                        event.note.copy(
                            isDone = event.isDone
                        )
                    )
                }
            }
            is NoteListEvent.OnNoteClick -> {
                sendUiEvent(UiEvent.OnNavigate(Routes.ADD_EDIT_NOTE + "?noteId=${event.note.id}"))
            }
            is NoteListEvent.OnUndoDeleteClick -> {
                deletedNote?.let {
                    viewModelScope.launch {
                        repository.addNote(it)
                    }
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}