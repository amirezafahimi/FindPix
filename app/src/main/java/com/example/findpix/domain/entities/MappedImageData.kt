package com.example.findpix.domain.entities

data class MappedImageData(
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