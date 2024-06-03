package com.nqmgaming.searchaddresslab.data.mapper

import com.nqmgaming.searchaddresslab.domain.model.Scoring as DomainScoring
import com.nqmgaming.searchaddresslab.data.remote.dto.Scoring

fun Scoring.asDomainScoring(): DomainScoring {
    return DomainScoring(
        queryScore = queryScore,
        fieldScore = fieldScore.asDomainFieldScore()
    )
}