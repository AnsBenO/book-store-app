package com.ansbeno.order_service.dto.orderevent;

import java.time.LocalDateTime;
import java.util.Set;

import com.ansbeno.order_service.domain.orderevent.OrderEventType;
import com.ansbeno.order_service.dto.OrderItemDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderEventDTO {
      private String eventId;
      private OrderEventType eventType;
      Set<OrderItemDTO> items;
      private String orderNumber;
      private LocalDateTime createdAt;
      private String payload;
}