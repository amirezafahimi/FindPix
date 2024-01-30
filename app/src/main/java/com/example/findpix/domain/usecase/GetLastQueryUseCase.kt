package com.example.findpix.domain.usecase

import com.example.findpix.domain.repository.SearchImageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetLastQueryUseCase@Inject constructor(
    private val repository: SearchImageRepository
) {
    /**
     * Executes the use case to retrieve the last search query from the repository.
     *
     * @return A flow emitting the last search query as a String.
     */
    fun run(): Flow<String> = flow {
        // Emit the last search query retrieved from the repository
        emit(repository.getLastQuery())
    }.flowOn(Dispatchers.IO) // Execute the flow on the IO dispatcher for background thread processing
}