package com.nqmgaming.searchaddresslab.presentation.screen.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nqmgaming.searchaddresslab.core.util.Constrains.API_KEY
import com.nqmgaming.searchaddresslab.core.util.Resources
import com.nqmgaming.searchaddresslab.domain.model.Response
import com.nqmgaming.searchaddresslab.domain.repository.HereRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val hereRepository: HereRepository
) : ViewModel() {

    private val _state = mutableStateOf(SearchScreenState())
    val state get() = _state

    fun onChangeQuery(newQuery: String) {
        _state.value = _state.value.copy(query = newQuery)
    }

    fun onEvent(event: SearchScreenEvent) {
        when (event) {
            is SearchScreenEvent.OnSearch -> onSearch(event.query, event.apiKey)
            SearchScreenEvent.OnSearchEmpty -> TODO()
            SearchScreenEvent.OnSearchError -> TODO()
            SearchScreenEvent.OnSearchLoading -> TODO()
            SearchScreenEvent.OnSearchNoInternet -> TODO()
            SearchScreenEvent.OnSearchNoLocation -> TODO()
            SearchScreenEvent.OnSearchNoResult -> TODO()
            SearchScreenEvent.OnSearchSuccess -> TODO()
        }
    }

    private var searchJob: Job? = null

    fun debounceQuery(scope: CoroutineScope, newQuery: String, delayMillis: Long = 500L) {
        searchJob?.cancel()
        searchJob = scope.launch {
            delay(delayMillis)
            onEvent(SearchScreenEvent.OnSearch(newQuery, API_KEY))
        }
    }

    private fun onSearch(query: String, apiKey: String) {
        viewModelScope.launch {
            hereRepository.getGeocode(query, apiKey).collect { result ->
                when (result) {
                    is Resources.Loading -> {
                        _state.value = _state.value.copy(
                            isLoading = result.isLoading
                        )
                    }

                    is Resources.Success -> {
                        _state.value = _state.value.copy(
                            addresses = result.data ?: Response(items = emptyList())
                        )
                    }

                    is Resources.Error -> {
                        _state.value = _state.value.copy(
                            isSearchError = true
                        )
                    }
                }

            }
        }
    }

}