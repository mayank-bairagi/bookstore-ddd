# 🚀 Understanding Domain-Driven Design (DDD) with a Simple Bookstore Example

Domain-Driven Design (DDD) is an approach that focuses on clearly understanding and modeling the core business domain of your application. Let's learn DDD practically using a straightforward, easy-to-follow Bookstore example.

---

## 📌 Step 1: Understand Your Domain

Our example domain is a simple **Online Bookstore**:

- Customers place orders for books.
- Books have inventory that must be managed.
- Orders must be confirmed, paid, and shipped.

> 💡 **Design Decision**: Clearly defining a business domain helps align the software structure closely with real-world concepts.

---

## 📌 Step 2: Identifying Entities and Value Objects

In DDD, we primarily work with two domain elements:

- **Entities**: Objects with a unique identity.  
  _Example: `Order`, `Customer`, `Book`_

- **Value Objects**: Objects identified by attributes only, without a unique identity.  
  _Example: `Address`, `ISBN`, `OrderItem`_

### ✅ Example Kotlin Code

```kotlin
// Entity: Customer (unique identity via customerId)
data class Customer(
    val customerId: UUID,
    val name: String,
    val email: String,
    val shippingAddress: Address
)

// Value Object: Address (immutable and interchangeable)
data class Address(
    val street: String,
    val city: String,
    val zipCode: String,
    val country: String
)
```

> 💡 **Design Decision**:  
> Use entities when objects have identity. Use value objects when identity doesn't matter, promoting immutability and simplicity.

---

## 📌 Step 3: Defining Aggregates (Consistency Boundaries)

**Aggregates** are clusters of domain objects treated as single units for data changes. Each aggregate has a **root entity** that controls all changes.

**Example**: _Order Aggregate_ (Aggregate Root: `Order`)

```kotlin
class Order(
    val orderId: UUID,
    val customer: Customer,
    val orderItems: List<OrderItem>
) {
    var orderStatus: OrderStatus = OrderStatus.NEW
        private set

    fun confirmOrder(paymentConfirmed: Boolean) {
        require(paymentConfirmed) { "Payment not confirmed" }
        orderStatus = OrderStatus.CONFIRMED
    }

    fun shipOrder() {
        require(orderStatus == OrderStatus.CONFIRMED) { "Order must be confirmed before shipping" }
        orderStatus = OrderStatus.SHIPPED
    }
}
```

> 💡 **Design Decision**:  
> Aggregates enforce invariants (business rules). Changes always happen through aggregate roots to maintain consistency.

---

## 📌 Step 4: Application Layer (Coordination & Workflow)

The application layer coordinates domain entities, repositories, and external systems. It **does not handle business logic itself**, rather orchestrates domain operations.

```kotlin
class OrderService(
    private val orderRepository: OrderRepository,
    private val paymentService: PaymentService,
    private val inventoryService: InventoryService
) {
    fun placeOrder(order: Order) {
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
            require(reserved) { "Insufficient stock" }
        }

        order.shipOrder()
        orderRepository.save(order)
    }
}
```

> 💡 **Design Decision**:  
> Application services handle external interactions and orchestration to keep domain entities pure and infrastructure-free.

---

## 📌 Step 5: Infrastructure Layer (Repositories & External Systems)

The infrastructure layer manages technical concerns like persistence, messaging, and external services. Here's a simple in-memory repository implementation for demonstration:

```kotlin
class InMemoryOrderRepository : OrderRepository {
    private val orders = mutableMapOf<UUID, Order>()

    override fun findById(orderId: UUID): Order? = orders[orderId]
    override fun save(order: Order) { orders[order.orderId] = order }
}
```

> 💡 **Design Decision**:  
> Separating repositories into the infrastructure layer isolates technical details from domain logic, allowing easy testing and future adaptability.

---

## 📌 DDD Layered Architecture Summary

Here’s a visual representation summarizing what we discussed:

```
[User Interface/API]
         |
         v
[Application Layer] --> (Coordinates domain and infrastructure)
         |
         v
[Domain Layer] --> (Entities, Value Objects, Aggregates, Domain Logic)
         |
         v
[Infrastructure Layer] --> (Repositories, External APIs, DBs)
```

### Quick Reminders:

- The **Domain Layer** never directly calls the infrastructure.
- The **Application Layer** coordinates interactions between Domain and Infrastructure.

---

## 🚩 Important Takeaways (DDD Best Practices)

- ✅ Keep domain entities free from infrastructure code.
- ✅ Entities handle domain logic; Services handle orchestration.
- ✅ Clearly define aggregates and enforce invariants at the root.
- ✅ Infrastructure details (e.g., databases) stay isolated from domain logic.

---

Happy Designing with DDD! 🎯