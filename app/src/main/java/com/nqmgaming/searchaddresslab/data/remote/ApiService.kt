package com.nqmgaming.searchaddresslab.data.remote

import com.nqmgaming.searchaddresslab.core.util.Constrains.API_KEY
import com.nqmgaming.searchaddresslab.core.util.Constrains.COUNTRY_CODE
import com.nqmgaming.searchaddresslab.core.util.Constrains.CURRENT_LOCATION
import com.nqmgaming.searchaddresslab.data.remote.dto.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("autocomplete")
    suspend fun getGeocode(
        @Query("q") q: String,
        @Query("apiKey") apiKey: String = API_KEY,
//        @Query("at") at: String = CURRENT_LOCATION, this is for auto suggest
//        @Query("in") inCountry: String = COUNTRY_CODE
    ): Response
}