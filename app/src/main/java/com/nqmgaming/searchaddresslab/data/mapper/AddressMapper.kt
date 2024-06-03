package com.nqmgaming.searchaddresslab.data.mapper

import com.nqmgaming.searchaddresslab.data.remote.dto.Address
import com.nqmgaming.searchaddresslab.domain.model.Address as DomainAddress

fun Address.asDomainAddress(): DomainAddress {
    return DomainAddress(
        city = city,
        countryCode = countryCode,
        countryName = countryName,
        county = county,
        district = district,
        label = label,
        postalCode = postalCode
    )
}