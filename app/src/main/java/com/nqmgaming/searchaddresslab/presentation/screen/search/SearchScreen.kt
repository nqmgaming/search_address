package com.nqmgaming.searchaddresslab.presentation.screen.search

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nqmgaming.searchaddresslab.presentation.screen.search.components.AddressItem

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchScreenViewModel = hiltViewModel(),
) {
    val state = viewModel.state.value
    val query = state.query
    val isLoading = state.isLoading
    val isSearchEmpty = state.isSearchEmpty
    val isSearchNoInternet = state.isSearchNoInternet
    val isSearchNoLocation = state.isSearchNoLocation
    val isSearchNoResult = state.isSearchNoResult
    val isSearchError = state.isSearchError
    val isSearchSuccess = state.isSearchSuccess
    val isSearchLoading = state.isSearchLoading
    val addresses = state.addresses

    val coroutineScope = rememberCoroutineScope()


    Column(
        modifier = modifier.fillMaxSize()
    ) {
        TextField(
            value = query,
            onValueChange = {
                viewModel.onChangeQuery(it)
                viewModel.debounceQuery(coroutineScope, it)
            }
        )
        LazyColumn {
            addresses.items?.let {
                items(it.size) { index ->
                    val address = it[index].address
                    AddressItem(
                        address = address ?: return@items
                    )
                }
            }
        }
    }


}