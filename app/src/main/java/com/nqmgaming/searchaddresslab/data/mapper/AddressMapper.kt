package com.nqmgaming.searchaddresslab.data.mapper

import com.nqmgaming.searchaddresslab.data.remote.dto.Address
import com.nqmgaming.searchaddresslab.domain.model.Address as DomainAddress

fun Address.asDomainAddress(): DomainAddress {
    return DomainAddress(
        label = label,
    )
}