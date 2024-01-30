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
    fun run(): Flow<String> = flow {
        emit(repository.getLastQuery())
    }.flowOn(Dispatchers.IO)
}