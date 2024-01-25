package com.example.findpix.domain.entities

import android.os.Parcelable

data class MappedImageItemModel(
    val imageId: Long = -1,
    val user: String,
    val url: String,
    val likes: String,
    val downloads: String,
    val comments: String,
    val views: String,
    val tags: List<String>,
    val largeImageURL: String?,
    val previewURL:String?,
    val userImageURL: String?
)



/*
fun ImageItem.toImageModel() = MappedImageItemModel(
    imageId = id?.toLong() ?: -1,
    user = user ?: "",
    url = previewURL ?: "",
    views = views.toString(),
    likes = likes.toString(),
    downloads = downloads.toString(),
    comments = comments.toString(),
    tags = tags?.split(", ") ?: emptyList(),
    largeImageURL = largeImageURL,
    previewURL = previewURL,
    userImageURL= userImageURL
)*/
