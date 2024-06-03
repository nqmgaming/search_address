package com.nqmgaming.searchaddresslab.data.mapper

import com.nqmgaming.searchaddresslab.data.remote.dto.Translations
import com.nqmgaming.searchaddresslab.domain.model.Translations as DomainTranslations

fun Translations?.asDomainTranslations(): DomainTranslations {
    return DomainTranslations(
        cityNames = this?.cityNames?.map { it.asDomainCityName() }
    )
}