package com.example.findpix

import com.example.findpix.data.source.local.entity.ImageData
import com.example.findpix.data.source.local.entity.LastSearchEntity

fun createMockLastSearch(): LastSearchEntity {
    return LastSearchEntity(
        query = "query",
        results = listOf(
            ImageData(
                imageId = 123,
                user = "testUser",
                likes = "10",
                downloads = "5",
                comments = "3",
                tags = listOf("home", "sea"),
                largeImageURL = "https://example.com/large4.jpg",
                previewURL = "https://example.com/preview4.jpg",
                userImageURL = "https://example.com/user4.jpg"
            )
        )
    )
}
