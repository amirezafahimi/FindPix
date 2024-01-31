package com.example.findpix.domain.entity

data class LastSearch(
    val query: String, // Searched query
    val imageItems: List<ImageItem> // Searching results
)