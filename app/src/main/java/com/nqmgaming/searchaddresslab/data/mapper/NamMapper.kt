package com.nqmgaming.searchaddresslab.data.mapper

import com.nqmgaming.searchaddresslab.data.remote.dto.Name
import com.nqmgaming.searchaddresslab.domain.model.Name as DomainName

fun Name.asDomainName(): DomainName {
    return DomainName(
        language = language,
        value = value,
        primary = primary,
        transliterated = transliterated,
        type = type
    )
}