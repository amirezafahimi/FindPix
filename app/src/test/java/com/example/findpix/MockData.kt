package com.example.findpix

import com.example.findpix.data.model.ImageResponse
import com.example.findpix.data.source.local.entity.ImageData
import com.example.findpix.data.source.local.entity.LastSearchEntity
import com.example.findpix.domain.entity.ImageItem


fun createMockImageItem(): ImageItem {
    return ImageItem(
        imageId = 123,
        user = "user",
        likes = "13",
        downloads = "2",
        comments = "2",
        tags = listOf("home", "sea"),
        largeImageURL = "https://example.com/large4.jpg",
        previewURL = "https://example.com/preview4.jpg",
        userImageURL = "https://example.com/user4.jpg"
    )
}

fun createMockImageResponses(): List<ImageResponse> {
    return listOf(
        ImageResponse(
            webformatHeight = 426,
            imageWidth = 4752,
            previewHeight = 99,
            webformatURL = "https://example.com/image4.jpg",
            userImageURL = "https://example.com/user4.jpg",
            previewURL = "https://example.com/preview4.jpg",
            comments = 50,
            imageHeight = 2500,
            tags = "home, sea",
            previewWidth = 100,
            downloads = 200,
            collections = 30,
            largeImageURL = "https://example.com/large4.jpg",
            views = 400,
            likes = 120,
            userId = 4
        ),
        ImageResponse(
            webformatHeight = 426,
            imageWidth = 4752,
            previewHeight = 99,
            webformatURL = "https://example.com/image5.jpg",
            userImageURL = "https://example.com/user5.jpg",
            previewURL = "https://example.com/preview5.jpg",
            comments = 80,
            imageHeight = 1800,
            tags = "mom, dad",
            previewWidth = 110,
            downloads = 250,
            collections = 40,
            largeImageURL = "https://example.com/large5.jpg",
            views = 450,
            likes = 130,
            userId = 5
        )
    )
}

fun createMockImageItems(): List<ImageItem> {
    return createMockImageResponses().map { it.mapToImageEntity() }
}

fun createMockLastSearch(query: String, results: List<ImageItem>): LastSearchEntity {
    return LastSearchEntity(query = query, results = results.map { it.mapToImageData() })
}

private fun ImageItem.mapToImageData(): ImageData {
    return ImageData(
        imageId = this.imageId,
        user = this.user,
        likes = this.likes,
        downloads = this.downloads,
        comments = this.comments,
        tags = this.getTags(),
        largeImageURL = this.largeImageURL ?: "",
        previewURL = this.previewURL ?: "",
        userImageURL = this.userImageURL ?: ""
    )
}

fun createMockImageDataList(): List<ImageItem> {
    // Create and return a list of mock ImageItem objects
    return listOf(ImageItem(
        imageId = 123,
        user = "testUser",
        likes = "10",
        downloads = "5",
        comments = "3",
        tags = listOf("nature", "landscape"),
        largeImageURL = "https://example.com/large_image.jpg",
        previewURL = "https://example.com/preview_image.jpg",
        userImageURL = "https://example.com/user_image.jpg"
    ))
}