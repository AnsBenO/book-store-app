package com.ansbeno.order_service.mappers;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.ansbeno.order_service.domain.order.Order;
import com.ansbeno.order_service.domain.order.OrderStatus;
import com.ansbeno.order_service.domain.orderitem.OrderItem;
import com.ansbeno.order_service.dto.CreateOrderRequestDTO;

public class OrderMapper {
      OrderMapper() {
      }

      public static Order mapRequestToEntity(CreateOrderRequestDTO request) {
            Order newOrder = Order.builder()
                        .orderNumber(UUID.randomUUID().toString())
                        .status(OrderStatus.NEW)
                        .customer(request.customer())
                        .deliveryAddress(request.deliveryAddress())
                        .build();

            Set<OrderItem> orderItems = request.items()
                        .stream()
                        .map(item -> OrderItemMapper.mapToOrderItemEntity(item, newOrder))
                        .collect(Collectors.toSet());

            newOrder.setItems(orderItems);

            return newOrder;
      }
}
