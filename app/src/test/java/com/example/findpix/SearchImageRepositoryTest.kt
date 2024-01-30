package com.example.findpix

import com.example.findpix.data.model.ImageResponse
import com.example.findpix.data.model.PixaBayResponse
import com.example.findpix.data.repository.SearchImageRepositoryImpl
import com.example.findpix.data.source.local.LocalDataSource
import com.example.findpix.data.source.local.entity.ImageData
import com.example.findpix.data.source.local.entity.LastSearch
import com.example.findpix.data.source.remote.PixaBayDataSource
import com.example.findpix.domain.entity.ImageItem
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class SearchImageRepositoryTest {

    @Mock
    private lateinit var mockPixaBayDataSource: PixaBayDataSource

    @Mock
    private lateinit var mockLocalDataSource: LocalDataSource

    @InjectMocks
    private lateinit var searchImageRepository: SearchImageRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `fetchDataByQuery success`() = runBlocking {

        val query = "query"
        val imageResponses = createMockImageResponses()
        val pixaBayResponse = PixaBayResponse(imageResponses, 4713, 100)

        `when`(mockPixaBayDataSource.fetchSearchResults(query)).thenReturn(pixaBayResponse)

        val result = searchImageRepository.fetchSearchResults(query)
        val expected = imageResponses.map { it.mapToImageEntity() }

        assertTrue(result.isNotEmpty())
        assertEquals(expected, result)
    }

    @Test(expected = IllegalStateException::class)
    fun `fetchSearchResults when results are empty`() = runBlocking {

        val query = "query"
        val pixaBayResponse = PixaBayResponse(emptyList(), 4713, 100)

        `when`(mockPixaBayDataSource.fetchSearchResults(query)).thenReturn(pixaBayResponse)

        val result = searchImageRepository.fetchSearchResults(query)

        assertEquals(IllegalStateException("Nothing found"), result)
    }

    @Test
    fun `getOfflineInitialData with data`() = runBlocking {

        val query = "query"
        val mockImageData = createMockImageItem()
        val mockSearchResult = createMockLastSearch(query, listOf(mockImageData))
        `when`(mockLocalDataSource.getLastSearchResult()).thenReturn(mockSearchResult)

        val result = searchImageRepository.getOfflineInitialData()

        assertEquals(query, result.query)
        assertEquals(1, result.imagesData.size)
        assertEquals(mockImageData.imageId, result.imagesData[0].imageId)
    }

    @Test
    fun `getOfflineInitialData without data`() = runBlocking {

        `when`(mockLocalDataSource.getLastSearchResult()).thenReturn(null)

        val result = searchImageRepository.getOfflineInitialData()

        assertEquals("fruits", result.query)
        assertEquals(0, result.imagesData.size)
    }

    @Test
    fun `getLastQuery with data`() = runBlocking {

        val query = "query"
        val mockSearchResult = createMockLastSearch(query, emptyList())
        `when`(mockLocalDataSource.getLastSearchResult()).thenReturn(mockSearchResult)

        val result = searchImageRepository.getLastQuery()

        assertEquals(query, result)
    }

    @Test
    fun `getLastQuery without data`() = runBlocking {

        `when`(mockLocalDataSource.getLastSearchResult()).thenReturn(null)

        val result = searchImageRepository.getLastQuery()

        assertEquals("fruits", result)
    }

    private fun createMockImageItem(): ImageItem {
        return ImageItem(
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
    }

    private fun createMockImageResponses(): List<ImageResponse> {
        return listOf(
            ImageResponse(
                webformatHeight = 426,
                imageWidth = 4752,
                previewHeight = 99,
                webformatURL = "https://pixabay.com/get/g62eef811a34bd9c15c9269ec772b3729e13b4c75a1578286df917a2397618f7c81e92e56163c97ff272a7e863e09f406_640.jpg",
                userImageURL = "https://cdn.pixabay.com/user/2012/03/08/00-13-48-597_250x250.jpg",
                previewURL = "https://cdn.pixabay.com/photo/2010/12/13/10/05/berries-2277_150.jpg",
                comments = 389,
                imageHeight = 3168,
                tags = "berries,fruits, food",
                previewWidth = 150,
                downloads = 502431,
                collections = 1780,
                largeImageURL = "https://pixabay.com/get/gd633530a25ff1a68c565f37e278c215b78650926392edf43841971bfc710c6dd449b49794fd4d786d7f7e926a6006695_1280.jpg",
                views = 944821,
                likes = 1889,
                userId = 1
            ),
            ImageResponse(
                webformatHeight = 426,
                imageWidth = 4752,
                previewHeight = 99,
                webformatURL = "https://pixabay.com/get/g62eef811a34bd9c15c9269ec772b3729e13b4c75a1578286df917a2397618f7c81e92e56163c97ff272a7e863e09f406_640.jpg",
                userImageURL = "https://cdn.pixabay.com/user/2012/03/08/00-13-48-597_250x250.jpg",
                previewURL = "https://cdn.pixabay.com/photo/2010/12/13/10/05/berries-2277_150.jpg",
                comments = 389,
                imageHeight = 3168,
                tags = "berries,fruits, food",
                previewWidth = 150,
                downloads = 502431,
                collections = 1780,
                largeImageURL = "https://pixabay.com/get/gd633530a25ff1a68c565f37e278c215b78650926392edf43841971bfc710c6dd449b49794fd4d786d7f7e926a6006695_1280.jpg",
                views = 944821,
                likes = 1889,
                userId = 2
            )
        )
    }

    private fun createMockLastSearch(query: String, results: List<ImageItem>): LastSearch {
        return LastSearch(query = query, results = results.map { it.mapToImageData() })
    }

    private fun ImageItem.mapToImageData(): ImageData {
        return ImageData(
            imageId = this.imageId,
            user = this.user,
            url = this.url,
            likes = this.likes,
            downloads = this.downloads,
            comments = this.comments,
            views = this.views,
            tags = this.getTags(),
            largeImageURL = this.largeImageURL ?: "",
            previewURL = this.previewURL ?: "",
            userImageURL = this.userImageURL ?: ""
        )
    }
}
