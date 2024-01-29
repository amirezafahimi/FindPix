package com.example.findpix.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.findpix.data.source.local.entity.ImageData

@Dao
interface PixaBayDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllImagesData(entities: List<ImageData>)
    @Query("SELECT * FROM image_data")
    fun getAllImagesData(): List<ImageData>
}