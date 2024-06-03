package com.nqmgaming.searchaddresslab.domain.repository

import com.nqmgaming.searchaddresslab.core.util.Resources
import com.nqmgaming.searchaddresslab.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface HereRepository {
    fun getGeocode(q: String, apiKey: String): Flow<Resources<Response>>
}