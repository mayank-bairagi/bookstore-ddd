package com.marketplace.domain.valueobjects

data class Address(
    val street: String,
    val city: String,
    val state: String?,
    val zipCode: String,
    val country: String
)
