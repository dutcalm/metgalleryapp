package com.example.metgalleryapp

import BottomNavBar
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.metgalleryapp.ui.SearchScreen
import com.example.metgalleryapp.ui.theme.CustomNavBar
import com.example.metgalleryapp.ui.theme.DetailScreen
import com.example.metgalleryapp.ui.theme.FavouritesScreen
import com.example.metgalleryapp.ui.theme.HomeScreen
import com.example.metgalleryapp.viewmodel.ArtViewModel

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ArtViewModel()

        setContent {
            val navController = rememberNavController()

            Scaffold(
                bottomBar = { BottomNavBar(navController = navController) }
            ) { paddingValues ->
                NavHost(
                    navController = navController,
                    startDestination = "home",
                    modifier = Modifier.padding(paddingValues)
                ) {

                    composable("home") {
                        Column(modifier = Modifier.fillMaxSize()) {
                            CustomNavBar(navController = navController)
                            HomeScreen(viewModel = viewModel(), navController = navController)
                        }
                    }
                    composable("search") {
                        Column(modifier = Modifier.fillMaxSize()) {
                            CustomNavBar(navController = navController)
                            SearchScreen(
                                viewModel,
                                onItemSelected = { id ->
                                    viewModel.loadArtDetails(id)
                                    navController.navigate("detail/$id")
                                },
                                onFavouritesClicked = {
                                    navController.navigate("favourites")
                                },
                                navController
                            )
                        }
                    }

                    composable("detail/{artId}") { backStackEntry ->
                        val artId = backStackEntry.arguments?.getString("artId")?.toIntOrNull()
                        if (artId != null) {
                            viewModel.loadArtDetails(artId)
                            Column(modifier = Modifier.fillMaxSize()) {
                                CustomNavBar(navController = navController)
                                DetailScreen(viewModel, navController)
                            }
                        } else {
                            Text("Invalid Art ID")
                        }
                    }

                    composable("favourites") {
                        Column(modifier = Modifier.fillMaxSize()) {
                            CustomNavBar(navController = navController)
                            FavouritesScreen(viewModel, navController) { id ->
                                viewModel.loadArtDetails(id)
                                navController.navigate("detail/$id")
                            }
                        }
                    }
                }
            }
        }
    }
}
