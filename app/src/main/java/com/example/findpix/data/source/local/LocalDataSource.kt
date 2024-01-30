package com.example.findpix.data.source.local

import com.example.findpix.data.source.local.entity.LastSearch
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
     * @return [LastSearch] representing the last saved search result, or null if not found.
     */
    override fun getLastSearchResult(): LastSearch? = appDao.getSearchResult()

    /**
     * Saves the provided search result to the local database.
     *
     * @param searchResult The [LastSearch] object to be saved.
     */
    override fun saveSearchResult(searchResult: LastSearch) {
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
     * @return [LastSearch] representing the last saved search result, or null if not found.
     */
    fun getLastSearchResult(): LastSearch?

    /**
     * Saves the provided search result to the local database.
     *
     * @param searchResult The [LastSearch] object to be saved.
     */
    fun saveSearchResult(searchResult: LastSearch)
}
