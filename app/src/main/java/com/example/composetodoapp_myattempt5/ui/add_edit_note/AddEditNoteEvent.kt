package com.example.composetodoapp_myattempt5.ui.add_edit_note

sealed class AddEditNoteEvent {
    data class OnTitleChange(val title: String): AddEditNoteEvent()
    data class OnTextChange(val text: String): AddEditNoteEvent()
    object OnSaveNoteClick: AddEditNoteEvent()
}
