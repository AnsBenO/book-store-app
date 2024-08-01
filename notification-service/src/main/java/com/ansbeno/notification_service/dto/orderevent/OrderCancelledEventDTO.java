package com.ansbeno.notification_service.dto.orderevent;

import java.time.LocalDateTime;
import java.util.Set;

import com.ansbeno.notification_service.dto.order.Address;
import com.ansbeno.notification_service.dto.order.Customer;
import com.ansbeno.notification_service.dto.orderitem.OrderItemDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderCancelledEventDTO extends OrderEventDTO {
      private Customer customer;
      private Address deliveryAddress;
      private String reason;

      public OrderCancelledEventDTO(String eventId, String orderNumber, Set<OrderItemDTO> items, Customer customer,
                  Address deliveryAddress, String reason, LocalDateTime createdAt) {
            super(eventId, OrderEventType.ORDER_CANCELLED, items, orderNumber, createdAt, null);
            this.items = items;
            this.customer = customer;
            this.deliveryAddress = deliveryAddress;
            this.reason = reason;
      }
}