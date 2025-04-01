package com.marketplace.application.services

import com.marketplace.domain.entities.Order
import com.marketplace.domain.repositories.OrderRepository
import com.marketplace.domain.services.ShippingCostCalculator
import java.util.UUID

class OrderService(
    private val orderRepository: OrderRepository,  // OrderRepository is only available through OrderService
    private val paymentService: PaymentService,
    private val inventoryService: InventoryService,
    private val shippingCostCalculator: ShippingCostCalculator // Domain Service

) {
    fun placeOrder(order: Order) {
        val shippingCost = shippingCostCalculator.calculateShippingCost(order)
        println("Calculated shipping cost: $shippingCost")
        orderRepository.save(order)
    }

    fun confirmOrder(orderId: UUID, paymentId: UUID) {
        val order = orderRepository.findById(orderId)
            ?: throw IllegalArgumentException("Order not found")

        val paymentConfirmed = paymentService.verifyPayment(paymentId, order.totalAmount)
        order.confirmOrder(paymentConfirmed)
        orderRepository.save(order)
    }

    fun shipOrder(orderId: UUID) {
        val order = orderRepository.findById(orderId)
            ?: throw IllegalArgumentException("Order not found")

        order.orderItems.forEach { item ->
            val reserved = inventoryService.reserveStock(item.book.bookId, item.quantity)
            require(reserved) { "Insufficient stock for product ${item.book.bookId}" }
        }

        order.shipOrder()
        orderRepository.save(order)
    }
}
