package com.example.findpix.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.findpix.data.source.local.entity.ImageData

@Database(entities = [ImageData::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class Database: RoomDatabase() {
    abstract fun pixaBayDao(): PixaBayDao
}