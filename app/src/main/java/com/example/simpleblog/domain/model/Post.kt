package com.example.simpleblog.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Post(
    val id: Int, // 1
    val title: String, // Hello world
    val content: String, // Hello world dang dang
    val publishedAt: String, // 2021-10-13T05:07:57.208Z
    val createdAt: String, // 2021-10-13T05:07:52.434Z
    val updatedAt: String // 2021-10-13T05:07:57.228Z
) : Parcelable