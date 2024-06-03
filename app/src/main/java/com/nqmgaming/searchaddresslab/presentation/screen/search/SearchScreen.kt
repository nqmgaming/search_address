package com.nqmgaming.searchaddresslab.presentation.screen.search

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nqmgaming.searchaddresslab.presentation.screen.search.components.AddressItem

@OptIn(ExperimentalMaterial3Api::class)
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
            },
            leadingIcon = {
                if (isSearchLoading || isLoading) {
                    CircularProgressIndicator()
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
            maxLines = 1,
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
            )
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp)
        ) {
            addresses.items?.let {
                items(it.size) { index ->
                    val address = it[index]
                    AddressItem(
                        item = address,
                        query = query
                    )
                }
            }
        }
    }
}