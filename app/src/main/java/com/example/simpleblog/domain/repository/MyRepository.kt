package com.example.simpleblog.domain.repository

import arrow.core.Either
import com.example.simpleblog.domain.model.Post
import com.example.simpleblog.common.MyFailure

interface MyRepository {
    suspend fun fetchPosts(): Either<MyFailure, List<Post>>
    suspend fun fetchPostDetail(id: Int): Either<MyFailure, Post>
    suspend fun createPost(title: String, content: String): Either<MyFailure, Post>
    suspend fun updatePost(id: Int, title: String, content: String): Either<MyFailure, Post>
    suspend fun deletePost(id: Int): Either<MyFailure, Post>
}