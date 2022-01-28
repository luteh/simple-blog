package com.example.simpleblog.data.remote

import com.example.simpleblog.data.remote.apiservice.MyApiService
import com.example.simpleblog.data.remote.request.PostRequest
import com.example.simpleblog.data.remote.response.PostResponse
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: MyApiService
) {
    suspend fun fetchPosts(): List<PostResponse> {
        return apiService.fetchPosts()
    }

    suspend fun fetchPostDetail(id: Int): PostResponse {
        return apiService.fetchPostDetail(id)
    }

    suspend fun createPost(postRequest: PostRequest): PostResponse {
        return apiService.createPost(postRequest)
    }

    suspend fun updatePost(
        id: Int,
        postRequest: PostRequest,
    ): PostResponse {
        return apiService.updatePost(id, postRequest)
    }

    suspend fun deletePost(id: Int): PostResponse {
        return apiService.deletePost(id)
    }
}