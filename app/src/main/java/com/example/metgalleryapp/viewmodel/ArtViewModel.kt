package com.example.metgalleryapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.metgalleryapp.data.model.ArtObject
import com.example.metgalleryapp.data.repository.MetRepository
import com.example.metgalleryapp.network.MetMuseumApi
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
    var selectedArt by mutableStateOf<ArtObject?>(null)

    fun searchArt(query: String) {
        viewModelScope.launch {
            searchResults = repository.searchArt(query).objectIDs
        }
    }

    fun loadArtDetails(id: Int) {
        viewModelScope.launch {
            selectedArt = repository.getArtObject(id)
        }
    }
}
