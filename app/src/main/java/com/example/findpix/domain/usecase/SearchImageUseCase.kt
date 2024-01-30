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
    fun run(query: String): Flow<List<ImageItem>> = flow {
        emit(repository.fetchSearchResults(query))
    }.flowOn(Dispatchers.IO)
}