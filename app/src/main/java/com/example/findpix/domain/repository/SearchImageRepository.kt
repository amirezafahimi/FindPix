package com.example.findpix.domain.repository

import com.example.findpix.domain.entity.LastSearchResult
import com.example.findpix.domain.entity.ImageItem

interface SearchImageRepository {
    suspend fun fetchSearchResults(query: String): List<ImageItem>
    suspend fun getOfflineInitialData(): LastSearchResult
    suspend fun getLastQuery(): String
}