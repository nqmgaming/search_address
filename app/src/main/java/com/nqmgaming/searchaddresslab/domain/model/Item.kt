package com.nqmgaming.searchaddresslab.domain.model

data class Item(
    val address: Address?,
    val id: String?,
    val localityType: String?,
    val mapView: MapView?,
    val position: Position?,
    val title: String?,
    var distance: Double?,
)