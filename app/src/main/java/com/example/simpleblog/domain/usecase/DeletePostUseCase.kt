package com.example.simpleblog.domain.usecase

import arrow.core.Either
import com.example.simpleblog.domain.model.Post
import com.example.simpleblog.domain.repository.MyRepository
import com.example.simpleblog.di.IoDispatcher
import com.example.simpleblog.common.MyFailure
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

data class DeletePostUseCaseParams(val id: Int)

class DeletePostUseCase @Inject constructor(
    private val myRepository: MyRepository,
    @IoDispatcher coroutineDispatcher: CoroutineDispatcher,
) :
    CoroutineUseCase<DeletePostUseCaseParams, Post>(coroutineDispatcher) {
    override suspend fun execute(params: DeletePostUseCaseParams): Either<MyFailure, Post> {
        return myRepository.deletePost(params.id)
    }
}