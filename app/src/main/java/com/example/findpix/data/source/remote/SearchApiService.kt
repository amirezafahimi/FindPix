package com.example.findpix.data.source.remote

import com.example.findpix.data.model.PixaBayResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface SearchApiService {

    @GET(".")
    suspend fun getSearchResponse(
        @Query("q") query: String,
        @Query("image_type") imageType: String = "photo",
        @Query("pretty") pretty: Boolean = true
    ): PixaBayResponse
}
