package com.example.findpix.data.repository

import com.example.findpix.data.source.remote.PixaBayDataSource
import com.example.findpix.domain.entities.MappedImageData
import javax.inject.Inject

/**
 * The class provides methods for searching for images from a remote server.
 */
class SearchImageRepositoryImpl @Inject constructor(
    private val pixaBayDataSource: PixaBayDataSource
) : SearchImageRepository {

    override suspend fun fetchSearchResults(query: String): List<MappedImageData> {
        return try {
            val result = pixaBayDataSource.fetchSearchResults(query = query)

            if (result.hits.isEmpty()) {
                throw IllegalStateException("Empty product list")
            }
            result.hits.map {
                it.mapToImageEntity()
            }
        } catch (e: Exception) {
            throw e
        }
    }
}

interface SearchImageRepository {
    suspend fun fetchSearchResults(query: String): List<MappedImageData>
}