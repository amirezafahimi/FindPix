package com.example.findpix

import com.example.findpix.domain.entity.ImageItem
import com.example.findpix.domain.entity.LastSearchResult
import com.example.findpix.domain.repository.SearchImageRepository
import com.example.findpix.domain.usecase.GetLastQueryUseCase
import com.example.findpix.domain.usecase.GetOfflineInitialDataUseCase
import com.example.findpix.domain.usecase.SearchImageUseCase
import com.example.findpix.ui.explore.ExploreViewModel
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.kotlin.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.whenever

// Define the test class
class ExploreViewModelTest {

    // Create mock objects for the dependencies of the ViewModel
    private var searchImageUseCase: SearchImageUseCase = mock()

    private var getOfflineInitialDataUseCase: GetOfflineInitialDataUseCase = mock()

    private var getLastQueryUseCase: GetLastQueryUseCase= mock()

    // Create a variable for the ViewModel
    private lateinit var viewModel: ExploreViewModel

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        MockitoAnnotations.initMocks(this)

        viewModel = ExploreViewModel(
            searchImageUseCase,
            getOfflineInitialDataUseCase,
            getLastQueryUseCase
        )

    }

    // Write a test method for the initOnlineMode() method of the ViewModel
    @Test
    fun `initOnlineMode should set UI state to not loading and update last query and search results`() = runTest {
        // Given a query returned by the getLastQueryUseCase
        val query = "query"
        whenever(getLastQueryUseCase.run()).thenReturn(flowOf(query))
        whenever(searchImageUseCase.run(query)).thenReturn(flowOf(createMockImageDataList()))

        // When the initOnlineMode() method is called
        viewModel.initOnlineMode()

        // Then the UI state should be updated accordingly
        val state = viewModel.uiState.value
        val lastQueryState = viewModel.lastQueryState.value
        assertEquals(query, lastQueryState)
        assertThat(state.isLoading).isFalse()
        assertThat(state.error).isNull()
        assertThat(state.success).isNotEmpty()
    }

    // Write a test method for the initOfflineMode() method of the ViewModel
    @Test
    fun `initOfflineMode should set UI state to not loading and update last query and search results`() = runTest {
        // Given a query returned by the getLastQueryUseCase
        val query = "query"
        whenever(getOfflineInitialDataUseCase.run()).thenReturn(flowOf(LastSearchResult(query, createMockImageDataList())))

        // When the initOfflineMode() method is called
        viewModel.initOfflineMode()

        // Then the UI state should be updated accordingly
        val state = viewModel.uiState.value
        val lastQueryState = viewModel.lastQueryState.value
        assertEquals(query, lastQueryState)
        assertThat(state.isLoading).isFalse()
        assertThat(state.error).isNull()
        assertThat(state.success).isNotEmpty()
    }

    // Write a test method for the searchImage() method of the ViewModel
    @Test
    fun `searchImage should update UI state with error in case of exception`() = runTest {
        val query = "query"
        whenever(searchImageUseCase.run(query)).thenReturn(
            flow {
                emit(Result.failure<List<ImageItem>>(IllegalStateException("Nothing found")).getOrThrow())
            }
        )
        viewModel.searchImage(query)
        val state = viewModel.uiState.value
        assertThat(state.success).isEmpty()
        assertThat(state.isLoading).isFalse()
        assertEquals("Nothing found", state.error)
    }

    private fun createMockImageDataList(): List<ImageItem> {
        // Create and return a list of mock ImageItem objects
        return listOf(ImageItem(
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
        ))
    }
}
