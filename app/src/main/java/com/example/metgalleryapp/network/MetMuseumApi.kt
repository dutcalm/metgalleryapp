package com.example.metgalleryapp.network

import com.example.metgalleryapp.data.model.ArtObject
import com.example.metgalleryapp.data.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MetMuseumApi {
    @GET("/public/collection/v1/search")
    suspend fun searchArt(@Query("q") query: String): SearchResponse

    @GET("/public/collection/v1/objects/{id}")
    suspend fun getArtObject(@Path("id") id: Int): ArtObject
}
