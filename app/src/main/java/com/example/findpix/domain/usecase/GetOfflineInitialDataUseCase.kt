package com.example.findpix.domain.usecase

import com.example.findpix.domain.entity.LastSearchResult
import com.example.findpix.domain.repository.SearchImageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetOfflineInitialDataUseCase @Inject constructor(
    private val repository: SearchImageRepository
) {
    /**
     * Executes the use case to retrieve offline initial data from the repository.
     *
     * @return A flow emitting the last search result as [LastSearchResult].
     */
    fun run(): Flow<LastSearchResult> = flow {
        // Emit the offline initial data retrieved from the repository
        emit(repository.getOfflineInitialData())
    }.flowOn(Dispatchers.IO) // Execute the flow on the IO dispatcher for background thread processing
}