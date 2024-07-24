package com.ansbeno.order_service.domain.order;

public record OrderSummary(String orderNumber, OrderStatus status) {
}