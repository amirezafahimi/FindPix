package com.example.findpix.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.findpix.data.source.local.ImageDataTypeConverter
import com.example.findpix.domain.entity.ImageItem

@Entity(tableName = "last_search_results")
data class LastSearch(
    @PrimaryKey
    val primaryKey: Int = 1,
    val query: String,
    @TypeConverters(ImageDataTypeConverter::class)
    val results: List<ImageData>
)

data class ImageData(
    val imageId: Long = -1,
    val user: String,
    val url: String,
    val likes: String,
    val downloads: String,
    val comments: String,
    val views: String,
    val tags: List<String>,
    val largeImageURL: String,
    val previewURL:String,
    val userImageURL: String
) {
    fun mapToImageEntity() = ImageItem(
        imageId = imageId,
        user = user,
        url = previewURL,
        views = views,
        likes = likes,
        downloads = downloads,
        comments = comments,
        tags = tags,
        largeImageURL = largeImageURL,
        previewURL = previewURL,
        userImageURL = userImageURL
    )
}