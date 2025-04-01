package com.marketplace.infrastructure

import com.marketplace.domain.entities.Order
import com.marketplace.domain.repositories.OrderRepository
import java.util.UUID

class InMemoryOrderRepository : OrderRepository {

    private val orders = mutableMapOf<UUID, Order>()

    override fun findById(orderId: UUID): Order? = orders[orderId]

    override fun save(order: Order) {
        orders[order.orderId] = order
    }
}
