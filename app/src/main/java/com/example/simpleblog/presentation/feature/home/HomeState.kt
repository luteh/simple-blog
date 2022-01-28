package com.example.simpleblog.presentation.feature.home

import com.example.simpleblog.domain.model.Post
import com.example.simpleblog.presentation.ui.ResultState

data class HomeState(
    val fetchPostsResult: ResultState<List<Post>>,
    val deletePostResult: ResultState<Post>,
    val showLoadingDialog:Boolean
) {
    companion object {
        fun initial(): HomeState {
            return HomeState(
                fetchPostsResult = ResultState.Initial(),
                deletePostResult = ResultState.Initial(),
                showLoadingDialog = false
            )
        }
    }
}
