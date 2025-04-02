package com.example.metgalleryapp.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.imageLoader
import coil.request.SuccessResult
import com.example.metgalleryapp.viewmodel.ArtViewModel

@Composable
fun DetailScreen(viewModel: ArtViewModel, navController: NavHostController) {
    val art = viewModel.selectedArt

    if (art != null) {
        val imageUrl = art.primaryImage
        var aspectRatio by remember { mutableStateOf(1f) }
        val context = LocalContext.current

        LaunchedEffect(imageUrl) {
            imageUrl?.let {
                val request = ImageRequest.Builder(context)
                    .data(it)
                    .build()

                val result = context.imageLoader.execute(request)
                if (result is SuccessResult) {
                    val bitmap = result.drawable.intrinsicWidth.toFloat() / result.drawable.intrinsicHeight.toFloat()
                    aspectRatio = bitmap
                }
            }
        }

        Column(modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF383E42))
            .verticalScroll(rememberScrollState())
        ) {

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .padding(16.dp, 8.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE24462),
                    contentColor = Color.White)
            ) {
                Text("Back")
            }

            imageUrl?.let {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(it)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Image for art ID: ${art.objectID}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .aspectRatio(aspectRatio),
                    contentScale = ContentScale.Fit
                )
            }

            Text(
                text = art.title ?: "Unknown Title",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(16.dp, 8.dp, 16.dp, 8.dp),
                color = Color.White
            )
            Text(
                text = "Artist: ${art.artistDisplayName ?: "Unknown"}",
                modifier = Modifier.padding(16.dp, 0.dp),
                color = Color.White
            )
            Text(
                text = "Department: ${art.department ?: "Unknown"}",
                modifier = Modifier.padding(16.dp, 0.dp, 16.dp, 8.dp),
                color = Color.White
            )

            val isFavorite = viewModel.isFavorite(art)

            Button(
                onClick = {
                    try {
                        if (isFavorite) {
                            viewModel.removeFromFavorites(art)
                            println("Removing from favorites: $art")
                        } else {
                            viewModel.addToFavorites(art)
                            println("Adding to favorites: $art")
                        }
                    } catch (e: Exception) {
                        println("Error while updating favorites: ${e.message}")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .padding(16.dp, 0.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE24462),
                    contentColor = Color.White)
            ) {
                Text(if (isFavorite) "Remove from Favorites" else "Add to Favorites")
            }
        }
    } else {
        Text(text = "Loading...", modifier = Modifier.padding(16.dp), color = Color.White)
    }
}

