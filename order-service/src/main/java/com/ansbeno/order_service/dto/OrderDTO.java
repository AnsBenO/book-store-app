package com.ansbeno.order_service.dto;

import java.time.LocalDateTime;
import java.util.Set;

import com.ansbeno.order_service.domain.order.Address;
import com.ansbeno.order_service.domain.order.Customer;
import com.ansbeno.order_service.domain.order.OrderStatus;

import lombok.Builder;

@Builder
public record OrderDTO(
            String orderNumber,
            String user,
            Set<OrderItemDTO> items,
            Customer customer,
            Address deliveryAddress,
            OrderStatus status,
            String comments,
            LocalDateTime createdAt) {
}
