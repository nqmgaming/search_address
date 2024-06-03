package com.nqmgaming.searchaddresslab.presentation.screen.search

sealed class SearchScreenEvent {
    data class OnSearch(val query: String, val apiKey: String) : SearchScreenEvent()
    data object OnSearchSuccess : SearchScreenEvent()
    data object OnSearchError : SearchScreenEvent()
    data object OnSearchLoading : SearchScreenEvent()
    data object OnSearchEmpty : SearchScreenEvent()
    data object OnSearchNoInternet : SearchScreenEvent()
    data object OnSearchNoLocation : SearchScreenEvent()
    data object OnSearchNoResult : SearchScreenEvent()

}