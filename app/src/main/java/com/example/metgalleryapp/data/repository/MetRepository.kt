package com.example.metgalleryapp.data.repository

import com.example.metgalleryapp.network.MetMuseumApi

class MetRepository(private val api: MetMuseumApi) {
    suspend fun searchArt(query: String) = api.searchArt(query)
    suspend fun getArtObject(id: Int) = api.getArtObject(id)
}
