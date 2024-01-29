package com.example.findpix.ui.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findpix.domain.entity.MappedImageData
import com.example.findpix.domain.usecase.GetInitialDataUseCase
import com.example.findpix.domain.usecase.SearchImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val searchImageUseCase: SearchImageUseCase,
    private val getInitialDataUseCase: GetInitialDataUseCase
) : ViewModel() {

    private val _lastQueryState: MutableStateFlow<String> = MutableStateFlow("")
    val lastQueryState: StateFlow<String> = _lastQueryState

    private val _uiState = MutableStateFlow(SearchResultState())
    val uiState: StateFlow<SearchResultState> = _uiState

    init {
        viewModelScope.launch {
            getInitialDataUseCase.run().collect {
                _lastQueryState.value = it.query
                _uiState.value = uiState.value.copy(
                    isLoading = false,
                    success = it.imagesData,
                )
            }
        }
    }

    fun searchImage(query: String) {
        _uiState.value = uiState.value.copy(
            isLoading = true
        )

        viewModelScope.launch {
            searchImageUseCase.run(query).catch { error ->
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
    val success: List<MappedImageData> = emptyList(),
)
