package com.example.findpix.data.repository

import com.example.findpix.data.source.local.LocalDataSource
import com.example.findpix.data.source.local.entity.ImageData
import com.example.findpix.data.source.local.entity.LastSearch
import com.example.findpix.data.source.remote.PixaBayDataSource
import com.example.findpix.domain.entity.LastSearchResult
import com.example.findpix.domain.entity.ImageItem
import com.example.findpix.domain.repository.SearchImageRepository
import javax.inject.Inject

/**
 * The class provides methods for searching for images from a remote server.
 */
class SearchImageRepositoryImpl @Inject constructor(
    private val pixaBayDataSource: PixaBayDataSource,
    private val localDataSource: LocalDataSource
) : SearchImageRepository {

    override suspend fun fetchSearchResults(query: String): List<ImageItem> {
        return try {
            val results = pixaBayDataSource.fetchSearchResults(query = query)
            if (results.hits.isEmpty()) {
                throw IllegalStateException("Nothing found")
            }
            results.hits.map {
                it.mapToImageEntity()
            }.also { images ->
                localDataSource.saveSearchResult(
                    LastSearch(
                        query = query,
                        results = images.map { image ->
                            ImageData(
                                imageId = image.imageId,
                                user = image.user,
                                url = image.url,
                                likes = image.likes,
                                downloads = image.downloads,
                                comments = image.comments,
                                views = image.views,
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

    override suspend fun getOfflineInitialData(): LastSearchResult {
        localDataSource.getLastSearchResult()?.let { search ->
            return LastSearchResult(search.query, search.results.map { it.mapToImageEntity() })
        } ?: return LastSearchResult("fruits", listOf())
    }

    override suspend fun getLastQuery(): String {
        localDataSource.getLastSearchResult()?.let { return it.query } ?: return "fruits"
    }
}
