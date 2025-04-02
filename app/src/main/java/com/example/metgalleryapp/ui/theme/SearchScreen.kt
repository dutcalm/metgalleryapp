package com.example.metgalleryapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.metgalleryapp.viewmodel.ArtViewModel

@Composable
fun SearchScreen(
    viewModel: ArtViewModel,
    onItemSelected: (Int) -> Unit,
    onFavouritesClicked: () -> Unit,
    navController: NavController
) {

    var text by remember { mutableStateOf(TextFieldValue("")) }

    LaunchedEffect(navController.currentBackStackEntry) {
        text = TextFieldValue("")
        viewModel.searchResults = emptyList()
    }

    Column(modifier = Modifier.background(Color(0xFF383E42))) {
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Search Art") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 16.dp, 16.dp, 8.dp)
                .clip(RoundedCornerShape(12.dp)),
            shape = RoundedCornerShape(12.dp)
        )

        Button(
            onClick = { viewModel.searchArt(text.text) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp)
                .clip(RoundedCornerShape(12.dp)),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFE24462),
                contentColor = Color.White
            )
        ) {
            Text("Search")
        }

        println("Rendering UI - searchResults: ${viewModel.searchResults}")

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(viewModel.searchResults ?: emptyList()) { id ->
                println("Displaying art object ID: $id")

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Art ID: $id",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(16.dp),
                        color = Color.White
                    )

                    Button(
                        onClick = { onItemSelected(id) },
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp)),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFE24462),
                            contentColor = Color.White,
                        )
                    ) {
                        Text("Details")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

