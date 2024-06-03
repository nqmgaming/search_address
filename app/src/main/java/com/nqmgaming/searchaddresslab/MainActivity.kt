package com.nqmgaming.searchaddresslab

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.nqmgaming.searchaddresslab.presentation.Screen
import com.nqmgaming.searchaddresslab.presentation.screen.home.HomeScreen
import com.nqmgaming.searchaddresslab.presentation.screen.search.SearchScreen
import com.nqmgaming.searchaddresslab.ui.theme.SearchAddressLabTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        enableEdgeToEdge()
        setContent {

            LaunchedEffect(key1 = Unit) {
                getLastKnownLocation()
            }

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
                            SearchScreen()
                        }
                    }
                }
            }
        }
    }

    fun getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    // Log the location
                    Log.d(
                        "Location",
                        "Latitude: ${location.latitude} Longitude: ${location.longitude}"
                    )
                }

            }

    }
}
