package com.example.simpleblog.presentation.feature.home

import com.example.simpleblog.presentation.feature.destinations.Destination
import com.ramcosta.composedestinations.spec.Direction

sealed interface HomeAction {
    object FetchPosts : HomeAction
    data class DeletePost(val id: Int) : HomeAction
    data class NavigateTo(val direction: Direction) : HomeAction

}