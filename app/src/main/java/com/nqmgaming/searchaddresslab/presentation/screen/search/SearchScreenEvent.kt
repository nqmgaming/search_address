package com.nqmgaming.searchaddresslab.presentation.screen.search

sealed class SearchScreenEvent {
    data class OnSearch(val query: String) : SearchScreenEvent()
    data object OnSearchSuccess : SearchScreenEvent()
    data object OnSearchError : SearchScreenEvent()
    data object OnSearchLoading : SearchScreenEvent()
}