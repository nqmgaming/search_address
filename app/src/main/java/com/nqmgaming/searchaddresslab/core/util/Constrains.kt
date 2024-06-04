package com.nqmgaming.searchaddresslab.core.util

import com.nqmgaming.searchaddresslab.BuildConfig

object Constrains {
    const val BASE_URL = "https://geocode.search.hereapi.com/v1/"
    const val API_KEY = BuildConfig.API_KEY
    const val CURRENT_LOCATION = "0,0"
    const val COUNTRY_CODE = "countryCode:VNM"
    const val LIMIT = 10
}