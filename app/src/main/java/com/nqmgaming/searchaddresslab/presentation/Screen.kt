package com.nqmgaming.searchaddresslab.presentation

sealed class Screen(val route: String) {
    data object HomeScreen : Screen("home_screen")
    data object SearchScreen : Screen("search_screen")
}
