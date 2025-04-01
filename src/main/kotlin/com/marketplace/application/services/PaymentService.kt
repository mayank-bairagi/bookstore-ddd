package com.marketplace.application.services

import java.math.BigDecimal
import java.util.UUID

interface PaymentService {
    fun verifyPayment(paymentId: UUID, amount: BigDecimal): Boolean
}


class MockPaymentService : PaymentService {
    override fun verifyPayment(paymentId: UUID, amount: BigDecimal): Boolean {
        // For demo purposes, always return true
        return true
    }
}

