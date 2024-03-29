package com.example.findpix.data.source.remote

import com.example.findpix.data.model.PixaBayResponse
import javax.inject.Inject

/**
 * Implementation of the [RemoteDataSource] interface that uses [SearchApiService] to fetch search results from PixaBay.
 *
 * @param searchApiService The service for interacting with the PixaBay API.
 */
class RemoteDataSourceImpl @Inject constructor(
    private val searchApiService: SearchApiService
) : RemoteDataSource {

    /**
     * Fetches search results from the PixaBay API based on the provided query.
     *
     * @param query The search query.
     * @return [PixaBayResponse] containing the search results.
     */
    override suspend fun fetchSearchResults(query: String): PixaBayResponse =
        searchApiService.getSearchResponse(query)
}

/**
 * Interface defining methods for fetching search results from the PixaBay data source.
 */
interface RemoteDataSource {
    /**
     * Fetches search results from the PixaBay API based on the provided query.
     *
     * @param query The search query.
     * @return [PixaBayResponse] containing the search results.
     */
    suspend fun fetchSearchResults(query: String): PixaBayResponse
}
