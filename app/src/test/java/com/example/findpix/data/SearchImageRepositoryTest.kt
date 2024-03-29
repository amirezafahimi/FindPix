package com.example.findpix.data

import com.example.findpix.createMockImageItem
import com.example.findpix.createMockImageResponses
import com.example.findpix.createMockLastSearch
import com.example.findpix.data.model.PixaBayResponse
import com.example.findpix.data.repository.SearchImageRepositoryImpl
import com.example.findpix.data.source.local.LocalDataSource
import com.example.findpix.data.source.remote.RemoteDataSource
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
    private lateinit var mockRemoteDataSource: RemoteDataSource

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

        `when`(mockRemoteDataSource.fetchSearchResults(query)).thenReturn(pixaBayResponse)

        val result = searchImageRepository.fetchSearchResults(query)
        val expected = imageResponses.map { it.mapToImageEntity() }

        assertTrue(result.isNotEmpty())
        assertEquals(expected, result)
    }

    @Test(expected = IllegalStateException::class)
    fun `fetchSearchResults when results are empty`() = runBlocking {

        val query = "query"
        val pixaBayResponse = PixaBayResponse(emptyList(), 4713, 100)

        `when`(mockRemoteDataSource.fetchSearchResults(query)).thenReturn(pixaBayResponse)

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
        assertEquals(1, result.imageItems.size)
        assertEquals(mockImageData.imageId, result.imageItems[0].imageId)
    }

    @Test
    fun `getOfflineInitialData without data`() = runBlocking {

        `when`(mockLocalDataSource.getLastSearchResult()).thenReturn(null)

        val result = searchImageRepository.getOfflineInitialData()

        assertEquals("fruits", result.query)
        assertEquals(0, result.imageItems.size)
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
}
