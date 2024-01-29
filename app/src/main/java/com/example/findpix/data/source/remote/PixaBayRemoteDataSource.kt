package com.example.findpix.data.source.remote

import com.example.findpix.data.model.PixaBayResponse
import javax.inject.Inject

/**
 * Data source to get data from server using api service client
 **/
class PixaBayDataSourceImpl @Inject constructor(
    private val searchApiService: SearchApiService
): PixaBayDataSource {
    override suspend fun fetchSearchResults(query: String): PixaBayResponse = searchApiService.getSearchResponse(query)
}

interface PixaBayDataSource {
    suspend fun fetchSearchResults(query: String): PixaBayResponse
}