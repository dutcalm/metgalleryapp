package com.example.metgalleryapp.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.metgalleryapp.data.model.ArtObject
import com.example.metgalleryapp.viewmodel.ArtViewModel

@Composable
fun HomeScreen(viewModel: ArtViewModel, navController: NavHostController) {
    val artObjects = viewModel.artObjects

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF383E42))
            .padding(16.dp)
    ) {
        Text(
            text = "Gallery",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (artObjects.isNotEmpty()) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(artObjects) { artObject ->
                    ArtItemCard(artObject, navController)
                }
            }
        } else {
            Text(
                text = "No Art Objects Available",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
        }
    }
}


@Composable
fun ArtItemCard(artObject: ArtObject, navController: NavHostController) {
    val context = LocalContext.current
    val imageUrl = artObject.primaryImage

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            imageUrl?.let {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(it)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Image for art ID: ${artObject.objectID}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = artObject.title ?: "Unknown Title",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )

            Button(
                onClick = {
                    navController.navigate("detail/${artObject.objectID}")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE24462),
                    contentColor = Color.White
                )
            ) {
                Text("View Details")
            }
        }
    }
}
