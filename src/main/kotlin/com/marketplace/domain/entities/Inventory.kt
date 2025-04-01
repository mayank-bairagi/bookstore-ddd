package com.marketplace.domain.entities

import java.util.UUID

data class InventoryItem(
    val productId: UUID,
    val productType: ProductType,
    var quantityAvailable: Int
)

enum class ProductType {
    BOOK,
    ELECTRONICS,
    CLOTHING,
    OTHER
}