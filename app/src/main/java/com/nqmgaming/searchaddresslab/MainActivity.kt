package com.nqmgaming.searchaddresslab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nqmgaming.searchaddresslab.presentation.Screen
import com.nqmgaming.searchaddresslab.presentation.screen.home.HomeScreen
import com.nqmgaming.searchaddresslab.presentation.screen.search.SearchScreen
import com.nqmgaming.searchaddresslab.ui.theme.SearchAddressLabTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SearchAddressLabTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        startDestination = Screen.HomeScreen.route,
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(
                            route = Screen.HomeScreen.route,
                            enterTransition = {
                                // bottom to top
                                slideInVertically(
                                    initialOffsetY = { -it },
                                    animationSpec = tween(500)
                                ) + fadeIn(animationSpec = tween(500))
                            },
                            exitTransition = {
                                slideOutVertically(
                                    targetOffsetY = { -it },
                                    animationSpec = tween(500)
                                ) + fadeOut(animationSpec = tween(500))
                            },
                            popEnterTransition = {
                                // top to bottom
                                slideInVertically(
                                    initialOffsetY = { it },
                                    animationSpec = tween(500)
                                ) + fadeIn(animationSpec = tween(500))
                            },
                            popExitTransition = {
                                slideOutVertically(
                                    targetOffsetY = { it },
                                    animationSpec = tween(500)
                                ) + fadeOut(animationSpec = tween(500))
                            }
                        ) {
                            HomeScreen(navController = navController)
                        }

                        composable(
                            route = Screen.SearchScreen.route,
                            exitTransition = {
                                slideOutVertically(
                                    targetOffsetY = { -it },
                                    animationSpec = tween(500)
                                ) + fadeOut(animationSpec = tween(500))
                            },
                            popEnterTransition = {
                                // top to bottom
                                slideInVertically(
                                    initialOffsetY = { it },
                                    animationSpec = tween(500)
                                ) + fadeIn(animationSpec = tween(500))
                            },
                            popExitTransition = {
                                slideOutVertically(
                                    targetOffsetY = { it },
                                    animationSpec = tween(500)
                                ) + fadeOut(animationSpec = tween(500))
                            }
                        ) {
                            SearchScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}
