package com.marketplace.domain.services

import com.marketplace.domain.entities.Order
import java.math.BigDecimal

class ShippingCostCalculator {
    fun calculateShippingCost(order: Order): BigDecimal {
        return if (order.customer.shippingAddress.country == "USA") BigDecimal(5) else BigDecimal(15)
    }
}