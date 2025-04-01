package com.marketplace.infrastructure

import com.marketplace.domain.entities.InventoryItem
import com.marketplace.domain.repositories.InventoryRepository
import java.util.UUID

class InMemoryInventoryRepository : InventoryRepository {

    private val inventoryItems = mutableMapOf<UUID, InventoryItem>()

    override fun findByProductId(productId: UUID): InventoryItem? = inventoryItems[productId]

    override fun save(inventoryItem: InventoryItem) {
        inventoryItems[inventoryItem.productId] = inventoryItem
    }

    override fun update(inventoryItem: InventoryItem) {
        inventoryItems[inventoryItem.productId] = inventoryItem
    }
}
