package com.marketplace.application.services

import com.marketplace.domain.entities.InventoryItem
import com.marketplace.domain.repositories.InventoryRepository
import java.util.UUID

class InventoryService(private val inventoryRepository: InventoryRepository) {

    fun reserveStock(productId: UUID, quantity: Int): Boolean {
        val inventory = inventoryRepository.findByProductId(productId)
            ?: throw IllegalArgumentException("Inventory item not found")

        return if (inventory.quantityAvailable >= quantity) {
            inventory.quantityAvailable -= quantity
            inventoryRepository.update(inventory)
            true
        } else {
            false
        }
    }
}
