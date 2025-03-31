package com.example.metgalleryapp.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import java.net.URL
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.asImageBitmap
import com.example.metgalleryapp.viewmodel.ArtViewModel

@Composable
fun DetailScreen(viewModel: ArtViewModel) {
    val art = viewModel.selectedArt
    var imagePainter by remember { mutableStateOf<Painter?>(null) }

    if (art != null) {
        art.primaryImage?.let { imageUrl ->
            LaunchedEffect(imageUrl) {
                val bitmap = loadImageFromUrl(imageUrl)
                imagePainter = BitmapPainter(bitmap.asImageBitmap())
            }
        }

        Column(modifier = Modifier.padding(16.dp)) {
            imagePainter?.let {
                Image(
                    painter = it,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().height(200.dp)
                )
            }
            Text(text = art.title ?: "Unknown Title", style = MaterialTheme.typography.titleLarge)
            Text(text = "Artist: ${art.artistDisplayName ?: "Unknown"}")
            Text(text = "Department: ${art.department ?: "Unknown"}")
        }
    } else {
        Text(text = "Loading...", modifier = Modifier.padding(16.dp))
    }
}

@SuppressLint("UseKtx")
fun loadImageFromUrl(url: String): Bitmap {
    return try {
        val inputStream = URL(url).openStream()
        BitmapFactory.decodeStream(inputStream)
    } catch (e: Exception) {
        Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
    }
}
