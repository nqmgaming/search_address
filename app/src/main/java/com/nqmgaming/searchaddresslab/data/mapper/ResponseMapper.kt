package com.nqmgaming.searchaddresslab.data.mapper

import com.nqmgaming.searchaddresslab.data.remote.dto.Response
import com.nqmgaming.searchaddresslab.domain.model.Response as DomainResponse

fun Response.toDomainResponse(): DomainResponse {
    return DomainResponse(
        items = items?.map { item ->
            com.nqmgaming.searchaddresslab.domain.model.Item(
                address = item.address?.asDomainAddress(),
                id = item.id,
                position = item.position?.asDomainPosition(),
                localityType = item.localityType,
                mapView = item.mapView?.asDomainMapView(),
                title = item.title,
                distance = item.distance
            )
        }
    )
}