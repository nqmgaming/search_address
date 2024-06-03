package com.nqmgaming.searchaddresslab.presentation.screen.search

import com.nqmgaming.searchaddresslab.domain.model.Response

data class SearchScreenState(
    val query: String = "",
    val isLoading: Boolean = false,
    val isSearchEmpty: Boolean = false,
    val isSearchNoInternet: Boolean = false,
    val isSearchNoLocation: Boolean = false,
    val isSearchNoResult: Boolean = false,
    val isSearchError: Boolean = false,
    val isSearchSuccess: Boolean = false,
    val isSearchLoading: Boolean = false,
    val addresses: Response = Response(
        items = emptyList()
    )
)