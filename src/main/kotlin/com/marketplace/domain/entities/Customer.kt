package com.marketplace.domain.entities

import com.marketplace.domain.valueobjects.Address
import java.util.UUID

data class Customer(
    val customerId: UUID,
    val name: String,
    val email: String,
    val shippingAddress: Address
)
