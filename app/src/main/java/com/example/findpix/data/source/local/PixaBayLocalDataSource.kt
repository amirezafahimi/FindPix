package com.example.findpix.data.source.local

import com.example.findpix.data.source.local.entity.ImageData
import com.example.findpix.data.source.local.entity.SearchResultEntity
import javax.inject.Inject

class PixaBayLocalDataSourceImpl @Inject constructor(
    private val appDao: AppDao
) : PixaBayLocalDataSource {
    override fun getLastSearchResult(): SearchResultEntity? = appDao.getSearchResult()

    override fun saveSearchResult(searchResult: SearchResultEntity) {
        appDao.insertSearchResult(searchResult)
    }
}

interface PixaBayLocalDataSource {
    fun getLastSearchResult(): SearchResultEntity?
    fun saveSearchResult(searchResult: SearchResultEntity)
}