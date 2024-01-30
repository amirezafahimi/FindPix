package com.example.findpix.data.source.local

import com.example.findpix.data.source.local.entity.LastSearch
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val appDao: AppDao
) : LocalDataSource {
    override fun getLastSearchResult(): LastSearch? = appDao.getSearchResult()

    override fun saveSearchResult(searchResult: LastSearch) {
        appDao.insertSearchResult(searchResult)
    }
}

interface LocalDataSource {
    fun getLastSearchResult(): LastSearch?
    fun saveSearchResult(searchResult: LastSearch)
}