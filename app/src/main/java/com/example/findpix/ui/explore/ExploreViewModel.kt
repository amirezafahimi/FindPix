package com.example.findpix.ui.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findpix.domain.entity.ImageItem
import com.example.findpix.domain.usecase.GetLastQueryUseCase
import com.example.findpix.domain.usecase.GetOfflineInitialDataUseCase
import com.example.findpix.domain.usecase.SearchImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val searchImageUseCase: SearchImageUseCase,
    private val getOfflineInitialDataUseCase: GetOfflineInitialDataUseCase,
    private val getLastQueryUseCase: GetLastQueryUseCase
) : ViewModel() {

    // StateFlow to hold the last search query
    private val _lastQueryState: MutableStateFlow<String> = MutableStateFlow("")
    val lastQueryState: StateFlow<String> = _lastQueryState

    // StateFlow to hold the UI state (loading, error, success)
    private val _uiState = MutableStateFlow(SearchResultState())
    val uiState: StateFlow<SearchResultState> = _uiState

    /**
     * Initialize the ViewModel in offline mode by fetching initial data from the repository.
     */
    fun initOfflineMode() {
        viewModelScope.launch {
            getOfflineInitialDataUseCase.run().collect {
                _lastQueryState.value = it.query
                _uiState.value = uiState.value.copy(
                    isLoading = false,
                    success = it.imagesData,
                )
            }
        }
    }

    /**
     * Initialize the ViewModel in online mode by fetching the last query and initiating a search.
     */
    fun initOnlineMode() {
        _uiState.value = uiState.value.copy(isLoading = true)

        viewModelScope.launch {
            getLastQueryUseCase.run().collect {
                _lastQueryState.value = it
                searchImage(it)
            }
        }
    }

    /**
     * Perform a search for images based on the provided query.
     *
     * @param query The search query for images.
     */
    fun searchImage(query: String) {
        _uiState.value = uiState.value.copy(isLoading = true)

        viewModelScope.launch {
            searchImageUseCase.run(query)
                .catch { error ->
                    onError(error.message.toString())
                }.collect {
                    _uiState.value = uiState.value.copy(
                        isLoading = false,
                        success = it,
                    )
                }
        }
    }

    /**
     * Handle errors by updating the UI state with the error message.
     *
     * @param error The error message.
     */
    private fun onError(error: String) {
        _uiState.value = uiState.value.copy(
            isLoading = false,
            error = error,
            success = emptyList(),
        )
    }
}

/**
 * Data class representing the state of the search results.
 *
 * @property isLoading Indicates whether the search is in progress.
 * @property error The error message, if any.
 * @property success The list of [ImageItem] representing the successful search results.
 */
data class SearchResultState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: List<ImageItem> = emptyList(),
)
