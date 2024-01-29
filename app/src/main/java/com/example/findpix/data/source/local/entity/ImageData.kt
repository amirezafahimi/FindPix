package com.example.findpix.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
@Entity(tableName = "image_data", primaryKeys = ["imageId"])
data class ImageData(

    @ColumnInfo(name = "imageId")
    val imageId: Long = -1,
    @ColumnInfo(name = "user")
    val user: String,
    @ColumnInfo(name = "url")
    val url: String,
    @ColumnInfo(name = "likes")
    val likes: String,
    @ColumnInfo(name = "downloads")
    val downloads: String,
    @ColumnInfo(name = "comments")
    val comments: String,
    @ColumnInfo(name = "views")
    val views: String,
    @ColumnInfo(name = "tags")
    val tags: List<String>,
    @ColumnInfo(name = "largeImageURL")
    val largeImageURL: String,
    @ColumnInfo(name = "previewURL")
    val previewURL:String,
    @ColumnInfo(name = "userImageURL")
    val userImageURL: String
)