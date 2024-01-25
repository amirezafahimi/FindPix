package com.example.findpix.domain.usecases

import com.example.findpix.data.repository.SearchImageRepository
import com.example.findpix.domain.entities.MappedImageData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SearchImageUseCase @Inject constructor(
    private val repository: SearchImageRepository
) {
    fun run(query: String): Flow<List<MappedImageData>> = flow {
        emit(repository.fetchSearchResults(query))
    }.flowOn(Dispatchers.IO)
}