package com.example.simpleblog.domain.usecase

import arrow.core.Either
import com.example.simpleblog.domain.model.Post
import com.example.simpleblog.domain.repository.MyRepository
import com.example.simpleblog.di.IoDispatcher
import com.example.simpleblog.common.MyFailure
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class FetchPostsUseCase @Inject constructor(
    private val myRepository: MyRepository,
    @IoDispatcher coroutineDispatcher: CoroutineDispatcher,
) :
    CoroutineUseCaseWithoutParams<List<Post>>(coroutineDispatcher) {
    override suspend fun execute(): Either<MyFailure, List<Post>> {
        return myRepository.fetchPosts()
    }
}