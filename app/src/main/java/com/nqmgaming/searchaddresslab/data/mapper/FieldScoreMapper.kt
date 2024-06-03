package com.nqmgaming.searchaddresslab.data.mapper

import com.nqmgaming.searchaddresslab.data.remote.dto.FieldScore
import com.nqmgaming.searchaddresslab.domain.model.FieldScore as DomainFieldScore

fun FieldScore.asDomainFieldScore(): DomainFieldScore {
    return DomainFieldScore(
        district = district
    )
}