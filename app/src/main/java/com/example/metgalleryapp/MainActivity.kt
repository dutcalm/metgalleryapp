package com.example.metgalleryapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.metgalleryapp.ui.DetailScreen
import com.example.metgalleryapp.ui.SearchScreen
import com.example.metgalleryapp.viewmodel.ArtViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ArtViewModel()
        setContent {
            var selectedId by remember { mutableStateOf<Int?>(null) }
            if (selectedId == null) {
                SearchScreen(viewModel) { id ->
                    selectedId = id
                    viewModel.loadArtDetails(id)
                }
            } else {
                DetailScreen(viewModel)
            }
        }
    }
}
