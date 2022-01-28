package com.example.simpleblog.data.remote.response


import com.example.simpleblog.common.orZero
import com.example.simpleblog.domain.model.Post
import com.google.gson.annotations.SerializedName

data class PostResponse(
    @SerializedName("id")
    val id: Int?, // 1
    @SerializedName("title")
    val title: String?, // Hello world
    @SerializedName("content")
    val content: String?, // Hello world dang dang
    @SerializedName("published_at")
    val publishedAt: String?, // 2021-10-13T05:07:57.208Z
    @SerializedName("created_at")
    val createdAt: String?, // 2021-10-13T05:07:52.434Z
    @SerializedName("updated_at")
    val updatedAt: String?   // 2021-10-13T05:07:57.228Z
) {
    fun toDomain(): Post {
        return Post(
            id.orZero(),
            title.orEmpty(),
            content.orEmpty(),
            publishedAt.orEmpty(),
            createdAt.orEmpty(),
            updatedAt.orEmpty()
        )
    }
}