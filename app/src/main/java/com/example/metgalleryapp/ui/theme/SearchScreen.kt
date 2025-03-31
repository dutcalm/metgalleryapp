package com.example.metgalleryapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.metgalleryapp.viewmodel.ArtViewModel
import androidx.compose.foundation.lazy.items


@Composable
fun SearchScreen(viewModel: ArtViewModel, onItemSelected: (Int) -> Unit) {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Search Art") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = { viewModel.searchArt(text.text) }, modifier = Modifier.padding(top = 8.dp)) {
            Text("Search")
        }
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(viewModel.searchResults ?: emptyList()) { id ->
                Text(
                    text = "Art ID: $id",
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.bodyLarge // Folosește un stil disponibil
                )
                Button(onClick = { onItemSelected(id) }) {
                    Text("View Details")
                }
            }
        }
    }
}
