package com.example.simpleblog.data.remote.apiservice

import com.example.simpleblog.data.remote.request.PostRequest
import com.example.simpleblog.data.remote.response.PostResponse
import retrofit2.http.*

interface MyApiService {

    @GET("/posts")
    suspend fun fetchPosts(): List<PostResponse>

    @GET("/posts/{id}")
    suspend fun fetchPostDetail(
        @Path("id") id: Int
    ): PostResponse

    @POST("/posts")
    suspend fun createPost(
        @Body postRequest: PostRequest
    ): PostResponse

    @PUT("/posts/{id}")
    suspend fun updatePost(
        @Path("id") id: Int,
        @Body postRequest: PostRequest
    ): PostResponse

    @DELETE("/posts/{id}")
    suspend fun deletePost(
        @Path("id") id: Int,
    ): PostResponse
}