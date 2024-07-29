package com.ansbeno.order_service.dto.orderevent;

import java.time.LocalDateTime;
import java.util.Set;

import com.ansbeno.order_service.domain.order.Address;
import com.ansbeno.order_service.domain.order.Customer;
import com.ansbeno.order_service.domain.orderevent.OrderEventType;
import com.ansbeno.order_service.dto.OrderItemDTO;

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
public class OrderDeliveredEventDTO extends OrderEventDTO {
      private Customer customer;
      private Address deliveryAddress;

      public OrderDeliveredEventDTO(String eventId, String orderNumber, Set<OrderItemDTO> items, Customer customer,
                  Address deliveryAddress, LocalDateTime createdAt) {
            super(eventId, OrderEventType.ORDER_DELIVERED, items, orderNumber, createdAt, null);
            this.items = items;
            this.customer = customer;
            this.deliveryAddress = deliveryAddress;
      }
}