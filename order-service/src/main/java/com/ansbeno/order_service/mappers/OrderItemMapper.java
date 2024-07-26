package com.ansbeno.order_service.mappers;

import com.ansbeno.order_service.domain.order.Order;
import com.ansbeno.order_service.domain.orderitem.OrderItem;
import com.ansbeno.order_service.dto.OrderItemDTO;

public class OrderItemMapper {

      OrderItemMapper() {
      }

      public static OrderItem mapToOrderItemEntity(OrderItemDTO orderItem, Order newOrder) {

            return OrderItem.builder()
                        .code(orderItem.code())
                        .name(orderItem.name())
                        .price(orderItem.price())
                        .order(newOrder)
                        .quantity(orderItem.quantity()).build();

      }
}
