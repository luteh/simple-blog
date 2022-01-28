package com.example.simpleblog.presentation.feature.postform

import com.example.simpleblog.domain.model.Post
import com.example.simpleblog.presentation.ui.ResultState

data class PostFormState(
    val addUpdatePostResult: ResultState<Post> = ResultState.Initial(),
    val title: String = "",
    val content: String = "",
    val id: Int? = null,
)
