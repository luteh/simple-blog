package com.example.simpleblog.domain.usecase

import arrow.core.Either
import com.example.simpleblog.domain.model.Post
import com.example.simpleblog.domain.repository.MyRepository
import com.example.simpleblog.di.IoDispatcher
import com.example.simpleblog.common.MyFailure
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

data class UpdatePostUseCaseParams(val id: Int, val title: String, val content: String)

class UpdatePostUseCase @Inject constructor(
    private val myRepository: MyRepository,
    @IoDispatcher coroutineDispatcher: CoroutineDispatcher,
) :
    CoroutineUseCase<UpdatePostUseCaseParams, Post>(coroutineDispatcher) {
    override suspend fun execute(params: UpdatePostUseCaseParams): Either<MyFailure, Post> {
        val (id, title, content) = params
        return myRepository.updatePost(id, title, content)
    }
}