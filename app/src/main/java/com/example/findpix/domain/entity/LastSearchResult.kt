package com.example.findpix.domain.entity

data class LastSearchResult(
    val query: String,
    val imagesData: List<ImageItem>
)