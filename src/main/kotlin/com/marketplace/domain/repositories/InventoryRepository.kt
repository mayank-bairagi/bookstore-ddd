package com.marketplace.domain.repositories

import com.marketplace.domain.entities.InventoryItem
import java.util.UUID

interface InventoryRepository {
    fun findByProductId(productId: UUID): InventoryItem?
    fun save(inventoryItem: InventoryItem)
    fun update(inventoryItem: InventoryItem)
}
