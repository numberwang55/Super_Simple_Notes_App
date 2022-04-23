package com.example.composetodoapp_myattempt5.util

sealed class UiEvent {
    data class OnNavigate(val route: String): UiEvent()
    object OnPopBackstack: UiEvent()
    data class OnShowSnackbar(
        val message: String,
        val action: String? = null
    ): UiEvent()
}