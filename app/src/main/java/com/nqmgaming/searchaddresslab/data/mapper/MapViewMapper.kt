package com.nqmgaming.searchaddresslab.data.mapper

import com.nqmgaming.searchaddresslab.data.remote.dto.MapView
import com.nqmgaming.searchaddresslab.domain.model.MapView as DomainMapView

fun MapView.asDomainMapView(): DomainMapView {
    return DomainMapView(
        east = east,
        north = north,
        south = south,
        west = west
    )
}