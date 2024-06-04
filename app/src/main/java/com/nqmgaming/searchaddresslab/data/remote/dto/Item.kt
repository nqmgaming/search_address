package com.nqmgaming.searchaddresslab.data.remote.dto

data class Item(
    val address: Address?,
    val id: String?,
    val localityType: String?,
    val mapView: MapView?,
    val politicalView: String?,
    val position: Position?,
    val title: String?,
    val distance: Double?,
)