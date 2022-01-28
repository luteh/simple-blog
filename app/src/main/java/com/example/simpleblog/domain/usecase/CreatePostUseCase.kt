package com.example.simpleblog.domain.usecase

import arrow.core.Either
import com.example.simpleblog.domain.model.Post
import com.example.simpleblog.domain.repository.MyRepository
import com.example.simpleblog.di.IoDispatcher
import com.example.simpleblog.common.MyFailure
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

data class CreatePostUseCaseParams(val title: String, val content: String)

class CreatePostUseCase @Inject constructor(
    private val myRepository: MyRepository,
    @IoDispatcher coroutineDispatcher: CoroutineDispatcher,
) :
    CoroutineUseCase<CreatePostUseCaseParams, Post>(coroutineDispatcher) {
    override suspend fun execute(params: CreatePostUseCaseParams): Either<MyFailure, Post> {
        val (title, content) = params
        return myRepository.createPost(title, content)
    }
}