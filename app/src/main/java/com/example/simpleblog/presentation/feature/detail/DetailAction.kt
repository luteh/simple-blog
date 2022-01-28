package com.example.simpleblog.presentation.feature.detail

sealed interface DetailAction {
    object OnClickEdit : DetailAction
    object OnRefresh : DetailAction
    data class OnInit(val id: Int) : DetailAction
}