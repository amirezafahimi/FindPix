package com.example.findpix.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.findpix.domain.entity.MappedImageData
import com.example.findpix.ui.detail.ImageDetailScreen
import com.example.findpix.ui.explore.ExploreScreen
import com.example.findpix.ui.theme.FindPixTheme
import com.example.findpix.util.NavDestinations
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FindPixTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = NavDestinations.EXPLORE_SCREEN) {
                        composable(NavDestinations.EXPLORE_SCREEN) {
                            ExploreScreen(onNextScreen = { imageData ->
                                navController.currentBackStackEntry?.savedStateHandle?.set(
                                    key = "imageData",
                                    value = imageData
                                )
                                navController.navigate(NavDestinations.DETAIL_SCREEN)
                            })
                        }

                        composable(NavDestinations.DETAIL_SCREEN) {
                            navController
                                .previousBackStackEntry
                                ?.savedStateHandle
                                ?.get<MappedImageData>("imageData")
                                ?.let {
                                    ImageDetailScreen(imageData = it) {
                                        navController.navigateUp()
                                    }
                                }
                        }
                    }
                }
            }
        }
    }
}