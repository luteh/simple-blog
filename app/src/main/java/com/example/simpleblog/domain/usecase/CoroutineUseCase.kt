package com.example.simpleblog.domain.usecase

import arrow.core.Either
import com.example.simpleblog.common.MyFailure
import com.example.simpleblog.common.parseToFailure
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext


abstract class CoroutineUseCase<in P, out R>(
    private val coroutineDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(params: P): Either<MyFailure, R> {
        return try {
            withContext(coroutineDispatcher) {
                execute(params)
            }
        } catch (e: Exception) {
            Either.Left(e.parseToFailure())
        }
    }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(params: P): Either<MyFailure, R>
}
