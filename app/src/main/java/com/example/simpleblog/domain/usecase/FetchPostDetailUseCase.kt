package com.example.simpleblog.domain.usecase

import arrow.core.Either
import com.example.simpleblog.domain.model.Post
import com.example.simpleblog.domain.repository.MyRepository
import com.example.simpleblog.di.IoDispatcher
import com.example.simpleblog.common.MyFailure
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

data class FetchPostDetailUseCaseParams(val id: Int)

class FetchPostDetailUseCase @Inject constructor(
    private val myRepository: MyRepository,
    @IoDispatcher coroutineDispatcher: CoroutineDispatcher,
) :
    CoroutineUseCase<FetchPostDetailUseCaseParams, Post>(coroutineDispatcher) {
    override suspend fun execute(params: FetchPostDetailUseCaseParams): Either<MyFailure, Post> {
        return myRepository.fetchPostDetail(params.id)
    }
}