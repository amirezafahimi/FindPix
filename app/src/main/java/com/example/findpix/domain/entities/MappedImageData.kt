package com.example.findpix.domain.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MappedImageData(
    val imageId: Long = -1,
    val user: String,
    val url: String,
    val likes: String,
    val downloads: String,
    val comments: String,
    val views: String,
    val tags: List<String>,
    val largeImageURL: String?,
    val previewURL:String?,
    val userImageURL: String?
): Parcelable {
    fun getTags(): List<String> {
        val selectedStrings = mutableListOf<String>()

        // Initialize the total length of the selected strings
        var totalLength = 0

        // Iterate over the original list
        for (string in tags) {
            // Calculate the size of the string
            val size = string.length

            // Add the string to the selected list if its size is less than 40
            if (totalLength + size < 40) {
                selectedStrings.add(string)
                totalLength += size
            }
        }
        return selectedStrings
    }
}