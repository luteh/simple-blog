package com.example.simpleblog.data.repository

import arrow.core.Either
import com.example.simpleblog.data.remote.RemoteDataSource
import com.example.simpleblog.data.remote.request.PostRequest
import com.example.simpleblog.domain.model.Post
import com.example.simpleblog.domain.repository.MyRepository
import com.example.simpleblog.common.MyFailure
import javax.inject.Inject

class MyRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource) :
    MyRepository {
    override suspend fun fetchPosts(): Either<MyFailure, List<Post>> {
        return Either.Right(remoteDataSource.fetchPosts().map { it.toDomain() })
    }

    override suspend fun fetchPostDetail(id: Int): Either<MyFailure, Post> {
        return Either.Right(remoteDataSource.fetchPostDetail(id).toDomain())
    }

    override suspend fun createPost(title: String, content: String): Either<MyFailure, Post> {
        return Either.Right(remoteDataSource.createPost(PostRequest(title, content)).toDomain())
    }

    override suspend fun updatePost(
        id: Int,
        title: String,
        content: String
    ): Either<MyFailure, Post> {
        return Either.Right(remoteDataSource.updatePost(id, PostRequest(title, content)).toDomain())
    }

    override suspend fun deletePost(id: Int): Either<MyFailure, Post> {
        return Either.Right(remoteDataSource.deletePost(id).toDomain())
    }
}