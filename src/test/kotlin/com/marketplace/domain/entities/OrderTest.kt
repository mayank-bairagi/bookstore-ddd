package com.marketplace.domain.entities

import com.marketplace.domain.valueobjects.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import java.math.BigDecimal
import java.util.UUID

class OrderTest {

    @Test
    fun `order total should be correctly calculated`() {
        val book = Book(UUID.randomUUID(), "DDD", "Eric Evans", BigDecimal(100), ISBN("1234567890"))
        val orderItem = OrderItem(book, 2)
        val order = Order(UUID.randomUUID(), mockCustomer, listOf(orderItem))

        assertEquals(BigDecimal(200), order.totalAmount)
    }

    @Test
    fun `order status transitions correctly`() {
        val order = Order(UUID.randomUUID(), mockCustomer, emptyList())

        order.confirmOrder(true)
        assertEquals(OrderStatus.CONFIRMED, order.orderStatus)

        order.shipOrder()
        assertEquals(OrderStatus.SHIPPED, order.orderStatus)
    }

    private val mockCustomer = Customer(UUID.randomUUID(), "Test", "test@example.com",
        Address("Street", "City", null, "0000", "Country"))
}
