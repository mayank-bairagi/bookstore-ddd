package com.marketplace.domain.entities

import com.marketplace.domain.valueobjects.OrderItem
import java.math.BigDecimal
import java.util.UUID

class Order(
    val orderId: UUID,
    val customer: Customer,
    val orderItems: List<OrderItem>
) {
    var orderStatus: OrderStatus = OrderStatus.NEW
        private set

    val totalAmount: BigDecimal = orderItems.sumOf { it.price }

    fun confirmOrder(paymentConfirmed: Boolean) {
        require(paymentConfirmed) { "Payment not confirmed" }
        orderStatus = OrderStatus.CONFIRMED
    }

    fun shipOrder() {
        require(orderStatus == OrderStatus.CONFIRMED) { "Order must be confirmed before shipping" }
        orderStatus = OrderStatus.SHIPPED
    }
}

enum class OrderStatus { NEW, CONFIRMED, SHIPPED, DELIVERED }
