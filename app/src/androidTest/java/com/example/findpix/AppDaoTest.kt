package com.example.findpix

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.findpix.data.source.local.AppDao
import com.example.findpix.data.source.local.Database
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDaoTest {

    private lateinit var database: Database
    private lateinit var appDao: AppDao

    @Before
    fun setUp() {
        // Create an in-memory database for testing
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            Database::class.java
        ).allowMainThreadQueries().build()

        appDao = database.appDao()
    }

    @After
    fun tearDown() {
        // Close the in-memory database after testing
        database.close()
    }

    @Test
    fun insertAndRetrieveImages() {
        val lastSearch = createMockLastSearch()

        appDao.insertSearchResult(lastSearch)

        val retrievedSearchRecord = appDao.getSearchResult()
        assertEquals(lastSearch.query, retrievedSearchRecord?.query)
        assertEquals(lastSearch.results.size, retrievedSearchRecord?.results?.size)
    }
}
