package com.nqmgaming.searchaddresslab.domain.model

data class Item(
    val address: Address,
    val id: String,
    val localityType: String,
    val mapView: MapView,
    val politicalView: String,
    val position: Position,
    val resultType: String,
    val scoring: Scoring,
    val title: String,
    val translations: Translations
)