package com.example.findpix.domain.usecase

import com.example.findpix.data.repository.SearchImageRepository
import com.example.findpix.domain.entity.LastSearchResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetOfflineInitialDataUseCase@Inject constructor(
    private val repository: SearchImageRepository
) {
    fun run(): Flow<LastSearchResult> = flow {
        emit(repository.getOfflineInitialData())
    }.flowOn(Dispatchers.IO)
}