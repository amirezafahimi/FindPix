package com.example.findpix.data.source.local

import androidx.room.TypeConverter
import com.example.findpix.data.source.local.entity.ImageData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ImageDataTypeConverter {
    @TypeConverter
    fun fromSearchResultItemList(value: List<ImageData>?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toSearchResultItemList(value: String): List<ImageData>? {
        val listType = object : TypeToken<List<ImageData>>() {}.type
        return Gson().fromJson(value, listType)
    }
}