package com.example.composetodoapp_myattempt5.ui.note_list

import com.example.composetodoapp_myattempt5.data.Note

sealed class NoteListEvent {
    data class OnDeleteNoteClick(val note: Note): NoteListEvent()
    object OnAddNoteClick: NoteListEvent()
    data class OnNoteClick(val note: Note): NoteListEvent()
    object OnUndoDeleteClick: NoteListEvent()
    data class OnDoneChange(val note: Note, val isDone: Boolean): NoteListEvent()
}
