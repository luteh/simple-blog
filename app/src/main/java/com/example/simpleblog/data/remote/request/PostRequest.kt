package com.example.simpleblog.data.remote.request


import com.google.gson.annotations.SerializedName

data class PostRequest(
    @SerializedName("title")
    val title: String, // Hello cui
    @SerializedName("content")
    val content: String // Hello world dang dang
)