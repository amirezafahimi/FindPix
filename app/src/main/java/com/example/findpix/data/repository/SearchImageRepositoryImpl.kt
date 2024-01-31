package com.example.findpix.data.repository

import com.example.findpix.data.source.local.LocalDataSource
import com.example.findpix.data.source.local.entity.ImageData
import com.example.findpix.data.source.local.entity.LastSearchEntity
import com.example.findpix.data.source.remote.RemoteDataSource
import com.example.findpix.domain.entity.LastSearch
import com.example.findpix.domain.entity.ImageItem
import com.example.findpix.domain.repository.SearchImageRepository
import javax.inject.Inject

class SearchImageRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : SearchImageRepository {

    /**
     * Fetches search results from the Pixabay API and saves the results locally.
     *
     * @param query The search query.
     * @return List of [ImageItem] representing the search results.
     * @throws IllegalStateException if no results are found.
     */
    override suspend fun fetchSearchResults(query: String): List<ImageItem> {
        return try {
            val results = remoteDataSource.fetchSearchResults(query = query)

            // Check if there are search results
            if (results.hits.isEmpty()) {
                throw IllegalStateException("Nothing found")
            }

            // Map Pixabay API results to local ImageItem entities
            results.hits.map {
                it.mapToImageEntity()
            }.also { images ->
                // Save the search results locally
                localDataSource.saveSearchResult(
                    LastSearchEntity(
                        query = query,
                        results = images.map { image ->
                            ImageData(
                                imageId = image.imageId,
                                user = image.user,
                                likes = image.likes,
                                downloads = image.downloads,
                                comments = image.comments,
                                tags = image.getTags(),
                                largeImageURL = image.largeImageURL ?: "",
                                previewURL = image.previewURL ?: "",
                                userImageURL = image.userImageURL ?: ""
                            )
                        }
                    )
                )
            }
        } catch (e: Exception) {
            throw e
        }
    }

    /**
     * Retrieves offline initial data, including the last search query and results.
     *
     * @return [LastSearch] containing the last search query and results.
     */
    override suspend fun getOfflineInitialData(): LastSearch {
        // Attempt to get the last search result from the local data source
        localDataSource.getLastSearchResult()?.let { search ->
            return LastSearch(search.query, search.results.map { it.mapToImageEntity() })
        } ?: return LastSearch("fruits", listOf()) // Default data if no offline data is available
    }

    /**
     * Retrieves the last search query.
     *
     * @return The last search query or a default value ("fruits" in this case).
     */
    override suspend fun getLastQuery(): String {
        // Attempt to get the last search result from the local data source
        localDataSource.getLastSearchResult()?.let {
            return it.query
        } ?: return "fruits" // Default value if no last query is available
    }
}
