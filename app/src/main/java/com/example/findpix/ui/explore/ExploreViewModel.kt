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

    private val _lastQueryState: MutableStateFlow<String> = MutableStateFlow("")
    val lastQueryState: StateFlow<String> = _lastQueryState

    private val _uiState = MutableStateFlow(SearchResultState())
    val uiState: StateFlow<SearchResultState> = _uiState

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

    fun initOnlineMode() {
        _uiState.value = uiState.value.copy(isLoading = true)

        viewModelScope.launch {
            getLastQueryUseCase.run().collect {
                _lastQueryState.value = it
                searchImage(it)
            }
        }
    }

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

    private fun onError(error: String) {
        _uiState.value = uiState.value.copy(
            isLoading = false,
            error = error,
            success = emptyList(),
        )
    }
}

data class SearchResultState(

    val isLoading: Boolean = false,
    val error: String? = null,
    val success: List<ImageItem> = emptyList(),
)
