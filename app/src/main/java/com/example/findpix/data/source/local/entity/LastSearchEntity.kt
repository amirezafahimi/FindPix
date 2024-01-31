package com.example.findpix.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.findpix.data.source.local.ImageDataTypeConverter
import com.example.findpix.domain.entity.ImageItem

@Entity(tableName = "last_search_results")
data class LastSearchEntity(
    @PrimaryKey
    val primaryKey: Int = 1,
    val query: String,
    @TypeConverters(ImageDataTypeConverter::class)
    val results: List<ImageData>
)

data class ImageData(
    val imageId: Long = -1,
    val user: String,
    val largeImageURL: String?,
    val previewURL:String?,
    val userImageURL: String?,
    val likes: String,
    val comments: String,
    val downloads: String,
    val tags: List<String>
) {
    fun mapToImageEntity() = ImageItem(
        imageId = imageId,
        user = user,
        likes = likes,
        downloads = downloads,
        comments = comments,
        tags = tags,
        largeImageURL = largeImageURL,
        previewURL = previewURL,
        userImageURL = userImageURL
    )
}