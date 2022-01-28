package com.example.simpleblog.presentation.ui

import com.ramcosta.composedestinations.spec.Direction

sealed interface UiEvent {
    data class PopBackStack<T>(val result: T) : UiEvent
    data class Navigate(val direction: Direction) : UiEvent
    data class ShowSnackbar(
        val message: String,
        val action: String? = null
    ) : UiEvent

    data class ShowToast(
        val message: String,
    ) : UiEvent

    object RemoveCurrentSavedState : UiEvent
    object Init : UiEvent
}
