package com.marketplace.domain.entities

import com.marketplace.domain.valueobjects.ISBN
import java.math.BigDecimal
import java.util.UUID

data class Book(
    val bookId: UUID,
    val title: String,
    val author: String,
    val price: BigDecimal,
    val isbn: ISBN
)
