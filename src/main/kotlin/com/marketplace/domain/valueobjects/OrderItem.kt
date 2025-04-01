package com.marketplace.domain.valueobjects

import com.marketplace.domain.entities.Book
import java.math.BigDecimal

data class OrderItem(
    val book: Book,
    val quantity: Int
) {
    val price: BigDecimal = book.price.multiply(quantity.toBigDecimal())
}
