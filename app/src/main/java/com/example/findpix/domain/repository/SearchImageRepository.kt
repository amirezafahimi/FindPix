package com.example.findpix.domain.repository

import com.example.findpix.domain.entity.LastSearch
import com.example.findpix.domain.entity.ImageItem

interface SearchImageRepository {
    suspend fun fetchSearchResults(query: String): List<ImageItem>
    suspend fun getOfflineInitialData(): LastSearch
    suspend fun getLastQuery(): String
}