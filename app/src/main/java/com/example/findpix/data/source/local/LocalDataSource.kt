package com.example.findpix.data.source.local

import com.example.findpix.data.source.local.entity.LastSearchEntity
import javax.inject.Inject

/**
 * Implementation of the [LocalDataSource] interface that uses [AppDao] to interact with local database for search results.
 *
 * @param appDao The Data Access Object (DAO) for interacting with the local database.
 */
class LocalDataSourceImpl @Inject constructor(
    private val appDao: AppDao
) : LocalDataSource {

    /**
     * Retrieves the last saved search result from the local database.
     *
     * @return [LastSearchEntity] representing the last saved search result, or null if not found.
     */
    override fun getLastSearchResult(): LastSearchEntity? = appDao.getSearchResult()

    /**
     * Saves the provided search result to the local database.
     *
     * @param searchResult The [LastSearchEntity] object to be saved.
     */
    override fun saveSearchResult(searchResult: LastSearchEntity) {
        appDao.insertSearchResult(searchResult)
    }
}

/**
 * Interface defining methods for interacting with local data storage for search results.
 */
interface LocalDataSource {

    /**
     * Retrieves the last saved search result from the local database.
     *
     * @return [LastSearchEntity] representing the last saved search result, or null if not found.
     */
    fun getLastSearchResult(): LastSearchEntity?

    /**
     * Saves the provided search result to the local database.
     *
     * @param searchResult The [LastSearchEntity] object to be saved.
     */
    fun saveSearchResult(searchResult: LastSearchEntity)
}
