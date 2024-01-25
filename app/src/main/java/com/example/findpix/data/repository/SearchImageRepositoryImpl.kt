package com.example.findpix.data.repository

import com.example.findpix.data.source.remote.PixaBayDataSource
import com.example.findpix.domain.entities.MappedImageItemModel
import javax.inject.Inject

/**
 * This class provides an interface for accessing image search data.
 */
class SearchImageRepositoryImpl @Inject constructor(
    private val pixaBayDataSource: PixaBayDataSource
) : SearchImageRepository {

    override suspend fun fetchSearchResults(query: String): List<MappedImageItemModel> {
        return try {
            val result = pixaBayDataSource.fetchSearchResults(query = query)

            if (result.hits.isEmpty()) {
                throw IllegalStateException("Empty product list")
            }
            result.hits.map {
                it.toImageModel()
            }
        } catch (e: Exception) {
            throw e
        }
    }
}

interface SearchImageRepository {
    suspend fun fetchSearchResults(query: String): List<MappedImageItemModel>
}