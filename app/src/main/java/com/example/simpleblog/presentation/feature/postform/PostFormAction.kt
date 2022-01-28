package com.example.simpleblog.presentation.feature.postform

sealed interface PostFormAction {
    data class OnChangeTitle(val title: String) : PostFormAction
    data class OnChangeContent(val content: String) : PostFormAction
    object OnClickAddUpdate : PostFormAction
    data class OnInit(val navArgs: PostFormScreenNavArgs) : PostFormAction
}