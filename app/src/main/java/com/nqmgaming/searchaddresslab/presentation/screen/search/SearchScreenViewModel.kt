package com.nqmgaming.searchaddresslab.presentation.screen.search

import android.util.Log
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

    private fun onEvent(event: SearchScreenEvent) {
        when (event) {
            is SearchScreenEvent.OnSearch -> onSearch(event.query)
            is SearchScreenEvent.OnSearchError -> onSearchError()
            is SearchScreenEvent.OnSearchLoading -> onSearchLoading()
            is SearchScreenEvent.OnSearchSuccess -> onSearchSuccess()
        }
    }


    private fun onSearchError() {
        _state.value = _state.value.copy(
            isSearchError = true
        )
    }

    private fun onSearchLoading() {
        _state.value = _state.value.copy(
            isLoading = true
        )
    }


    private fun onSearchSuccess() {
        _state.value = _state.value.copy(
            isSearchSuccess = true
        )
    }


    private var searchJob: Job? = null

    fun debounceQuery(scope: CoroutineScope, newQuery: String, delayMillis: Long = 1000L) {
        searchJob?.cancel()
        if(newQuery.isNotEmpty()){
            searchJob = scope.launch {
                delay(delayMillis)
                onEvent(SearchScreenEvent.OnSearch(newQuery))
            }
        }else{
            _state.value = _state.value.copy(
                addresses = Response(items = emptyList())
            )
        }
    }

    private fun onSearch(query: String) {
        viewModelScope.launch {
            hereRepository.getAddresses(query).collect { result ->
                when (result) {
                    is Resources.Loading -> {
                        _state.value = _state.value.copy(
                            isLoading = result.isLoading
                        )
                    }

                    is Resources.Success -> {
                        _state.value = _state.value.copy(
                            addresses = result.data ?: Response(items = emptyList()),
                            isSearchSuccess = true
                        )
                        Log.d("SearchScreenViewModel", "onSearch: ${result.data}")
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