package com.example.metgalleryapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.metgalleryapp.network.MetMuseumApi
import com.example.metgalleryapp.data.model.ArtObject
import com.example.metgalleryapp.data.repository.MetRepository
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ArtViewModel : ViewModel() {
    private val api = Retrofit.Builder()
        .baseUrl("https://collectionapi.metmuseum.org")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MetMuseumApi::class.java)
    private val repository = MetRepository(api)

    var searchResults by mutableStateOf<List<Int>?>(null)
    var artObjects by mutableStateOf<List<ArtObject>>(emptyList())
    var selectedArt by mutableStateOf<ArtObject?>(null)

    private val _favorites = mutableStateListOf<ArtObject>()
    val favorites: List<ArtObject> get() = _favorites

    suspend fun getArtObject(id: Int): ArtObject {
        return api.getArtObject(id)
    }

    fun loadArtDetails(id: Int) {
        viewModelScope.launch {
            try {
                selectedArt = repository.getArtObject(id)
            } catch (e: Exception) {
                println("Error loading art details: ${e.message}")
            }
        }
    }

    fun addToFavorites(art: ArtObject) {
        try {
            if (!isFavorite(art)) {
                _favorites.add(art)
            }
        } catch (e: Exception) {
            println("Error adding to favorites: ${e.message}")
        }
    }

    fun removeFromFavorites(art: ArtObject) {
        try {
            _favorites.remove(art)
        } catch (e: Exception) {
            println("Error removing from favorites: ${e.message}")
        }
    }

    fun isFavorite(art: ArtObject): Boolean {
        return _favorites.any { it.objectID == art.objectID }
    }

    fun clearFavourites() {
        _favorites.clear()
    }

    fun searchArt(query: String) {
        searchResults = emptyList()
        viewModelScope.launch {
            try {
                val response = repository.searchArt(query)
                println("Search results: ${response.objectIDs}")
                searchResults = response.objectIDs
                fetchArtDetails()
            } catch (e: Exception) {
                println("Error in searchArt: ${e.message}")
            }
        }
    }

    fun fetchArtDetails() {
        viewModelScope.launch {
            try {
                val validObjects = mutableListOf<ArtObject>()
                searchResults?.filterNotNull()?.forEach { id ->
                    try {
                        val artObject = repository.getArtObject(id)
                        validObjects.add(artObject)
                    } catch (e: Exception) {
                        println("Skipping ID $id due to error: ${e.message}")
                    }
                }
                artObjects = validObjects
            } catch (e: Exception) {
                println("Error fetching art details: ${e.message}")
            }
        }
    }
}
