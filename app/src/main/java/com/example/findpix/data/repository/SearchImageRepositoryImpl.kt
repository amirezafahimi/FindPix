package com.example.findpix.data.repository

import com.example.findpix.data.source.local.PixaBayLocalDataSource
import com.example.findpix.data.source.local.entity.ImageData
import com.example.findpix.data.source.local.entity.SearchResultEntity
import com.example.findpix.data.source.remote.PixaBayDataSource
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
            val result = if(query != "x") {
                pixaBayDataSource.fetchSearchResults(query = query).hits.map {
                    it.mapToImageEntity()
                }
                /*if (result.hits.isEmpty()) {
                    throw IllegalStateException("Empty product list")
                }*/
            } else pixaBayLocalDataSource.getLastSearchResult().results.map {
                it.mapToImageEntity()
            }

            result.also { images ->
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

    /*override suspend fun getInitialData(): List<MappedImageData> {
        val result = pixaBayLocalDataSource.getLastSearchResult().results.map { it.mapToImageEntity() }
        if(result.isNotEmpty()) {

        } else {

        }
    }*/
}

interface SearchImageRepository {
    suspend fun fetchDataByQuery(query: String): List<MappedImageData>
    //suspend fun getInitialData(): List<MappedImageData>
}