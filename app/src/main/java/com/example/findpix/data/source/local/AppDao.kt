package com.example.findpix.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.findpix.data.source.local.entity.LastSearch

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSearchResult(searchResult: LastSearch)

    @Query("SELECT * FROM last_search_results WHERE primaryKey = 1")
    fun getSearchResult(): LastSearch?

}