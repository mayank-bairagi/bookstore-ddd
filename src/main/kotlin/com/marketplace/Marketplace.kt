package com.marketplace

import com.marketplace.application.services.*
import com.marketplace.domain.entities.*
import com.marketplace.domain.repositories.*
import com.marketplace.domain.valueobjects.*
import com.marketplace.infrastructure.*
import java.math.BigDecimal
import java.util.UUID

fun main() {
    // Setup repositories (in-memory)
    val orderRepository: OrderRepository = InMemoryOrderRepository()
    val inventoryRepository: InventoryRepository = InMemoryInventoryRepository()

    // Setup services
    val paymentService: PaymentService = MockPaymentService()
    val inventoryService = InventoryService(inventoryRepository)
    val orderService = OrderService(orderRepository, paymentService, inventoryService)

    // Initialize inventory
    val bookId = UUID.randomUUID()
    inventoryRepository.save(InventoryItem(bookId, ProductType.BOOK, 10))

    // Create domain objects
    val customer = Customer(UUID.randomUUID(), "John Doe", "john@example.com",
        Address("123 Elm Street", "Springfield", "IL", "62704", "USA"))

    val book = Book(bookId, "Clean Architecture", "Robert C. Martin", BigDecimal(50), ISBN("9780134494166"))
    val orderItem = OrderItem(book, 2)
    val order = Order(UUID.randomUUID(), customer, listOf(orderItem))

    // Place order
    orderService.placeOrder(order)

    // Confirm order
    val paymentId = UUID.randomUUID()
    orderService.confirmOrder(order.orderId, paymentId)

    // Ship order
    orderService.shipOrder(order.orderId)

    // Verify the final state
    val shippedOrder = orderRepository.findById(order.orderId)
    println("Final order status: ${shippedOrder?.orderStatus}")
}
