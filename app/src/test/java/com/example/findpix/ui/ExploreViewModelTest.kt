package com.example.findpix.ui

import com.example.findpix.createMockImageDataList
import com.example.findpix.domain.entity.ImageItem
import com.example.findpix.domain.entity.LastSearch
import com.example.findpix.domain.usecase.GetLastQueryUseCase
import com.example.findpix.domain.usecase.GetOfflineInitialDataUseCase
import com.example.findpix.domain.usecase.SearchImageUseCase
import com.example.findpix.ui.explore.ExploreViewModel
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.MockitoAnnotations
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
        whenever(getOfflineInitialDataUseCase.run()).thenReturn(flowOf(LastSearch(query, createMockImageDataList())))

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
}
