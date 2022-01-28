package com.example.simpleblog.presentation.feature.detail

import com.example.simpleblog.domain.model.Post
import com.example.simpleblog.presentation.ui.ResultState

data class DetailState(
    val fetchPostDetailResult: ResultState<Post> = ResultState.Initial(),
    val id: Int = 0
)
