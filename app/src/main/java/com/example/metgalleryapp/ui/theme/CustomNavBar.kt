package com.example.metgalleryapp.ui.theme

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomNavBar(navController: NavHostController) {
    TopAppBar(
        title = {
            Text(
                text = "Metropolitan Museum of Art",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 8.dp),
            )
        },

        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFFE24462)
        )
    )
}
