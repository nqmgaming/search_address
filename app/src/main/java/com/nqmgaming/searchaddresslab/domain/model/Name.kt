package com.nqmgaming.searchaddresslab.domain.model

data class Name(
    val language: String,
    val primary: Boolean,
    val transliterated: Boolean,
    val type: String,
    val value: String
)