package com.example.findpix.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.findpix.data.source.local.entity.LastSearchEntity

@Database(entities = [LastSearchEntity::class], version = 1, exportSchema = false)
@TypeConverters(ImageDataTypeConverter::class)
abstract class Database: RoomDatabase() {
    abstract fun appDao(): AppDao
}