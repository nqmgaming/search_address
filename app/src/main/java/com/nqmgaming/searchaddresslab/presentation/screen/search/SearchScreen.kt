package com.nqmgaming.searchaddresslab.presentation.screen.search

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.nqmgaming.searchaddresslab.R
import com.nqmgaming.searchaddresslab.core.util.CalculateDistance.calculateDistance
import com.nqmgaming.searchaddresslab.core.util.NetworkUtils
import com.nqmgaming.searchaddresslab.presentation.screen.search.components.AddressItem

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchScreenViewModel = hiltViewModel(),
) {

    val context = LocalContext.current

    val state = viewModel.state.value
    val query = state.query
    val isLoading = state.isLoading
    val isSearchLoading = state.isSearchLoading
    val addresses = state.addresses

    val coroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current

    val networkIsConnected = NetworkUtils.isInternetAvailable(context)

    val isPlaying by remember {
        mutableStateOf(true)
    }
    val speed by remember {
        mutableFloatStateOf(1f)
    }
    val composition by rememberLottieComposition(

        LottieCompositionSpec
            .RawRes(R.raw.no_internet)
    )

    // to control the animation
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = isPlaying,
        speed = speed,
        restartOnPlay = false

    )

    val locationPermission = rememberPermissionState(
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(
            context
        )

    val lat = remember {
        mutableDoubleStateOf(0.0)
    }
    val lng = remember {
        mutableDoubleStateOf(0.0)
    }

    LaunchedEffect(key1 = lat.doubleValue, key2 = lng.doubleValue) {
        locationPermission.launchPermissionRequest()

        if (locationPermission.status.isGranted) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return@LaunchedEffect
            }
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                // Got last known location. In some rare situations this can be null.
                if (location != null) {
                    // Logic to handle location object
                    try {
                        lat.doubleValue = location.latitude
                        lng.doubleValue = location.longitude
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    if (networkIsConnected) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
        ) {
            TextField(
                value = query,
                onValueChange = {
                    viewModel.onChangeQuery(it)
                    viewModel.debounceQuery(coroutineScope, it)
                },
                leadingIcon = {
                    if (isSearchLoading || isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colorScheme.primary,
                            strokeWidth = 2.dp,
                            strokeCap = ProgressIndicatorDefaults.LinearStrokeCap
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search here"
                        )
                    }
                },
                trailingIcon = {
                    if (query.isNotEmpty()) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Search here",
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .clickable {
                                    viewModel.onChangeQuery("")
                                    viewModel.debounceQuery(coroutineScope, "")
                                }
                        )
                    }

                },
                shape = RoundedCornerShape(30.dp),
                singleLine = true,
                placeholder = { Text(text = "Search") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .border(
                        width = 1.dp,
                        color = Color.Gray.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(30.dp)
                    ),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = MaterialTheme.colorScheme.onSurface
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search,
                    keyboardType = KeyboardType.Text
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        viewModel.debounceQuery(coroutineScope, query)
                        keyboardController?.hide()
                    }
                ),

                )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 8.dp)
            ) {

                addresses.items?.let { items ->
                    items.forEach { address ->
                        address.distance = calculateDistance(
                            lat1 = lat.doubleValue,
                            lon1 = lng.doubleValue,
                            lat2 = address.position?.lat ?: 0.0,
                            lon2 = address.position?.lng ?: 0.0
                        )
                    }

                    val sortedItems = items.sortedBy { address -> address.distance }

                    items(sortedItems.size) { index ->
                        val address = sortedItems[index]
                        AddressItem(
                            item = address,
                            query = query,
                        )
                    }
                }

                if (addresses.items.isNullOrEmpty() && !isSearchLoading && query.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
                            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_location_off),
                                contentDescription = "Here Maps Search Address Lab",
                                modifier = Modifier
                                    .size(100.dp),
                                contentScale = ContentScale.Crop
                            )
                            Text(
                                text = "No results found",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }

                if (query.isEmpty()) {
                    item {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
                            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                                contentDescription = "Here Maps Search Address Lab",
                                modifier = Modifier
                                    .size(200.dp),
                                contentScale = ContentScale.Crop
                            )
                            Text(
                                text = "Search for an address",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }


            }

        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
        ) {
            LottieAnimation(
                composition,
                progress,
                modifier = Modifier.size(400.dp)
            )
            Text(
                text = "No Internet Connection",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}