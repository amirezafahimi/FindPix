package com.example.findpix.domain.usecase

import com.example.findpix.data.repository.SearchImageRepository
import com.example.findpix.domain.entity.LastSearchResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetLastQueryUseCase@Inject constructor(
    private val repository: SearchImageRepository
) {
    fun run(): Flow<String> = flow {
        emit(repository.getLastQuery())
    }.flowOn(Dispatchers.IO)
}