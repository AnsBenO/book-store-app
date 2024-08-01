package com.ansbeno.notification_service.dto.orderevent;

import java.time.LocalDateTime;
import java.util.Set;

import com.ansbeno.notification_service.dto.orderitem.OrderItemDTO;

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