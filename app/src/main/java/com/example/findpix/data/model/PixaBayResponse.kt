package com.example.findpix.data.model

import com.example.findpix.domain.entity.ImageItem
import com.google.gson.annotations.SerializedName

data class PixaBayResponse(

    @field:SerializedName("hits")
    val hits: List<ImageResponse> = listOf(),

    @field:SerializedName("total")
    val total: Int? = null,

    @field:SerializedName("totalHits")
    val totalHits: Int? = null
)

data class ImageResponse(

    @field:SerializedName("webformatHeight")
    val webformatHeight: Int? = null,

    @field:SerializedName("imageWidth")
    val imageWidth: Int? = null,

    @field:SerializedName("previewHeight")
    val previewHeight: Int? = null,

    @field:SerializedName("webformatURL")
    val webformatURL: String? = null,

    @field:SerializedName("userImageURL")
    val userImageURL: String? = null,

    @field:SerializedName("previewURL")
    val previewURL: String? = null,

    @field:SerializedName("comments")
    val comments: Int? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("imageHeight")
    val imageHeight: Int? = null,

    @field:SerializedName("tags")
    val tags: String? = null,

    @field:SerializedName("previewWidth")
    val previewWidth: Int? = null,

    @field:SerializedName("downloads")
    val downloads: Int? = null,

    @field:SerializedName("collections")
    val collections: Int? = null,

    @field:SerializedName("user_id")
    val userId: Int? = null,

    @field:SerializedName("largeImageURL")
    val largeImageURL: String? = null,

    @field:SerializedName("pageURL")
    val pageURL: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("imageSize")
    val imageSize: Int? = null,

    @field:SerializedName("webformatWidth")
    val webformatWidth: Int? = null,

    @field:SerializedName("user")
    val user: String? = null,

    @field:SerializedName("views")
    val views: Int? = null,

    @field:SerializedName("likes")
    val likes: Int? = null
) {
    fun mapToImageEntity() = ImageItem(
        imageId = id?.toLong() ?: -1,
        user = user ?: "",
        likes = likes.toString(),
        downloads = downloads.toString(),
        comments = comments.toString(),
        tags = tags?.split(", ") ?: emptyList(),
        largeImageURL = largeImageURL,
        previewURL = previewURL,
        userImageURL = userImageURL
    )
}