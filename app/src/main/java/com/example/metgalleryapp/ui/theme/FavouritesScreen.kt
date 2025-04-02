package com.example.metgalleryapp.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.metgalleryapp.viewmodel.ArtViewModel

@Composable
fun FavouritesScreen(viewModel: ArtViewModel, navController: NavHostController, onItemSelected: (Int) -> Unit) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFF383E42))) {

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text("Favourite Artworks", style = MaterialTheme.typography.headlineSmall, color = Color.White)

            Button(onClick = { viewModel.clearFavourites() },
                modifier = Modifier.clip(RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE24462),
                    contentColor = Color.White)){
                Text("Clear All")
            }
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(viewModel.favorites) { art ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onItemSelected(art.objectID) }
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(art.primaryImage),
                        contentDescription = "Favourite Art",
                        modifier = Modifier
                            .size(100.dp)
                            .padding(end = 8.dp)
                    )
                    Column {
                        Text(text = art.title ?: "Unknown Title", style = MaterialTheme.typography.bodyLarge, color = Color.White)
                        Text(text = "Artist: ${art.artistDisplayName ?: "Unknown"}", color = Color.White)
                    }
                }
            }
        }
    }
}
