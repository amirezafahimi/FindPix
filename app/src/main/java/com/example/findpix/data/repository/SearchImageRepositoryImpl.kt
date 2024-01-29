package com.example.findpix.data.repository

import com.example.findpix.data.source.local.PixaBayLocalDataSource
import com.example.findpix.data.source.local.entity.ImageData
import com.example.findpix.data.source.local.entity.SearchResultEntity
import com.example.findpix.data.source.remote.PixaBayDataSource
import com.example.findpix.domain.entity.LastSearchResult
import com.example.findpix.domain.entity.MappedImageData
import javax.inject.Inject

/**
 * The class provides methods for searching for images from a remote server.
 */
class SearchImageRepositoryImpl @Inject constructor(
    private val pixaBayDataSource: PixaBayDataSource,
    private val pixaBayLocalDataSource: PixaBayLocalDataSource
) : SearchImageRepository {

    override suspend fun fetchDataByQuery(query: String): List<MappedImageData> {
        return try {
            val results = pixaBayDataSource.fetchSearchResults(query = query)
            if (results.hits.isEmpty()) {
                throw IllegalStateException("Nothing found")
            }
            results.hits.map {
                it.mapToImageEntity()
            }.also { images ->
                pixaBayLocalDataSource.saveSearchResult(
                    SearchResultEntity(
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

    override suspend fun fetchOnlineInitialData(): LastSearchResult {
        pixaBayLocalDataSource.getLastSearchResult()?.let { search ->
            return LastSearchResult(search.query, fetchDataByQuery(query = search.query))
        } ?: return LastSearchResult("fruits", fetchDataByQuery("fruits"))
    }

    override suspend fun getOfflineInitialData(): LastSearchResult {
        pixaBayLocalDataSource.getLastSearchResult()?.let { search ->
            return LastSearchResult(search.query, search.results.map { it.mapToImageEntity() })
        } ?: return LastSearchResult("fruits", listOf())
    }
}

interface SearchImageRepository {
    suspend fun fetchDataByQuery(query: String): List<MappedImageData>
    suspend fun fetchOnlineInitialData(): LastSearchResult
    suspend fun getOfflineInitialData(): LastSearchResult
}