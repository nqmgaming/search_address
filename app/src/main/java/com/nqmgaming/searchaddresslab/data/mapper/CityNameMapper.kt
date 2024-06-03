package com.nqmgaming.searchaddresslab.data.mapper

import com.nqmgaming.searchaddresslab.data.remote.dto.CityName
import com.nqmgaming.searchaddresslab.domain.model.CityName as DomainCityName

fun CityName.asDomainCityName(): DomainCityName {
    return DomainCityName(
        names = names?.map { it.asDomainName() },
        preference = preference
    )
}