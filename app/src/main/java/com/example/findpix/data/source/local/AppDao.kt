package com.example.findpix.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.findpix.data.source.local.entity.ImageData
import com.example.findpix.data.source.local.entity.SearchResultEntity

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSearchResult(searchResult: SearchResultEntity)

    @Query("SELECT * FROM last_search_results WHERE primaryKey = 1")
    fun getSearchResult(): SearchResultEntity?

}