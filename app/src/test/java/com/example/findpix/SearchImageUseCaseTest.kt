package com.example.findpix

import com.example.findpix.domain.entity.ImageItem
import com.example.findpix.domain.repository.SearchImageRepository
import com.example.findpix.domain.usecase.SearchImageUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
class SearchImageUseCaseTest {

    @Mock
    private lateinit var repository: SearchImageRepository

    private lateinit var useCase: SearchImageUseCase

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        useCase = SearchImageUseCase(repository)
    }

    @Test
    fun `run emits search results`() = runTest {

        val query = "query"
        val expectedResults = createMockImageItems()

        `when`(repository.fetchSearchResults(query)).thenReturn(expectedResults)

        val result: Flow<List<ImageItem>> = useCase.run(query)

        result.collect { emittedResults ->
            assertEquals(expectedResults, emittedResults)
        }
    }

    @Test
    fun `run produce exception when fetchSearchData called`() = runTest {
        val query = "query"
        `when`(repository.fetchSearchResults(query))
            .doThrow(IllegalStateException("Nothing found"))

        try {
            useCase.run(query)
        } catch (e: java.lang.IllegalStateException) {
            assertEquals("Nothing found", e.message)
        }
    }
}
