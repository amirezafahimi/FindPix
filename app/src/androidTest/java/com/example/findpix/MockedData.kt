package com.example.findpix

import com.example.findpix.data.source.local.entity.ImageData
import com.example.findpix.data.source.local.entity.LastSearch
import com.example.findpix.domain.entity.ImageItem

fun createMockLastSearch(): LastSearch {
    return LastSearch(
        query = "query",
        results = listOf(
            ImageData(
                imageId = 123,
                user = "testUser",
                url = "https://example.com/image.jpg",
                likes = "10",
                downloads = "5",
                comments = "3",
                views = "100",
                tags = listOf("nature", "landscape"),
                largeImageURL = "https://example.com/large_image.jpg",
                previewURL = "https://example.com/preview_image.jpg",
                userImageURL = "https://example.com/user_image.jpg"
            )
        )
    )
}
