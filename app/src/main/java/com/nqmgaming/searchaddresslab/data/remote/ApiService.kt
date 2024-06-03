package com.nqmgaming.searchaddresslab.data.remote

import com.nqmgaming.searchaddresslab.data.remote.dto.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("autosuggest")
    suspend fun getGeocode(
        @Query("q") q: String,
        @Query("apiKey") apiKey: String,
        @Query("at") at: String = "0,0",
        @Query("in") inCountry: String = "countryCode:VNM"
    ): Response
}