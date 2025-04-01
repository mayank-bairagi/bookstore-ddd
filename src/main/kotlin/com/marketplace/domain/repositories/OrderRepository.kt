package com.marketplace.domain.repositories

import com.marketplace.domain.entities.Order
import java.util.UUID

interface OrderRepository {
    fun findById(orderId: UUID): Order?
    fun save(order: Order)
}
