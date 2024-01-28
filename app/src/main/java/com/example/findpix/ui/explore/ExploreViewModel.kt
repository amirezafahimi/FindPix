package com.example.findpix.ui.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findpix.domain.entities.MappedImageData
import com.example.findpix.domain.usecases.SearchImageUseCase
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
    private val searchImageUseCase: SearchImageUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchResultState())
    val uiState: StateFlow<SearchResultState> = _uiState

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    fun searchImage(query: String) {

        _uiState.value = uiState.value.copy(
            isLoading = true
        )

        viewModelScope.launch {
            flowOf(query)
                .debounce(300)
                .filter { query ->
                    return@filter query.isNotEmpty()
                }
                .distinctUntilChanged()
                .flatMapLatest { query ->
                    searchImageUseCase.run(query).catch { error ->
                        onError(error.message.toString())
                    }
                }
                .flowOn(Dispatchers.Default)
                .catch { error ->
                    onError(error.message.toString())
                }
                .collect {
                    _uiState.value = uiState.value.copy(
                        isLoading = false,
                        success = it,
                        currentImageNode = null
                    )
                }
        }
    }

    private fun onError(error: String) {
        _uiState.value = uiState.value.copy(
            isLoading = false,
            error = error,
            success = emptyList(),
            currentImageNode = null
        )
    }
}

data class SearchResultState(

    val isLoading: Boolean = false,
    val error: String? = null,
    val success: List<MappedImageData> = emptyList(),
    val currentImageNode: MappedImageData? = null
)
