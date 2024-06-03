package com.nqmgaming.searchaddresslab.data.mapper

import com.nqmgaming.searchaddresslab.data.remote.dto.Position
import kotlin.math.ln
import com.nqmgaming.searchaddresslab.domain.model.Position as DomainPosition

fun Position.asDomainPosition(): DomainPosition {
    return DomainPosition(
        lat = lat,
        lng = lng
    )
}