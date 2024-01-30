package com.example.findpix.domain.usecase

import com.example.findpix.domain.entity.ImageItem
import com.example.findpix.domain.repository.SearchImageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SearchImageUseCase @Inject constructor(
    private val repository: SearchImageRepository
) {
    /**
     * Executes the use case to search for images based on the provided query.
     *
     * @param query The search query for images.
     * @return A flow emitting a list of [ImageItem] representing the search results.
     */
    fun run(query: String): Flow<List<ImageItem>> = flow {
        // Emit the search results retrieved from the repository
        emit(repository.fetchSearchResults(query))
    }.flowOn(Dispatchers.IO) // Execute the flow on the IO dispatcher for background thread processing
}