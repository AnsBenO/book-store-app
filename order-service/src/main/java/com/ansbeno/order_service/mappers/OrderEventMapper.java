package com.ansbeno.order_service.mappers;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.ansbeno.order_service.domain.order.Order;
import com.ansbeno.order_service.domain.orderevent.OrderEvent;
import com.ansbeno.order_service.dto.OrderItemDTO;
import com.ansbeno.order_service.dto.orderevent.OrderCancelledEventDTO;
import com.ansbeno.order_service.dto.orderevent.OrderCreatedEventDTO;
import com.ansbeno.order_service.dto.orderevent.OrderDeliveredEventDTO;
import com.ansbeno.order_service.dto.orderevent.OrderDeliveryInProgressEventDTO;
import com.ansbeno.order_service.dto.orderevent.OrderErrorEventDTO;
import com.ansbeno.order_service.dto.orderevent.OrderEventDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class OrderEventMapper {

      private static final ObjectMapper objectMapper;
      static {
            objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
      }

      private OrderEventMapper() {
      }

      public static OrderEvent toOrderEvent(OrderEventDTO event) throws JsonProcessingException {

            return OrderEvent.builder()
                        .eventId(event.getEventId())
                        .eventType(event.getEventType())
                        .orderNumber(event.getOrderNumber())
                        .createdAt(event.getCreatedAt())
                        .payload(objectMapper.writeValueAsString(event))
                        .build();
      }

      public static OrderCreatedEventDTO buildOrderCreatedEvent(Order order) {
            return new OrderCreatedEventDTO(
                        UUID.randomUUID().toString(),
                        order.getOrderNumber(),
                        getOrderItems(order),
                        order.getCustomer(),
                        order.getDeliveryAddress(),
                        LocalDateTime.now());
      }

      public static OrderDeliveredEventDTO buildOrderDeliveredEvent(Order order) {
            return new OrderDeliveredEventDTO(
                        UUID.randomUUID().toString(),
                        order.getOrderNumber(),
                        getOrderItems(order),
                        order.getCustomer(),
                        order.getDeliveryAddress(),
                        LocalDateTime.now());
      }

      public static OrderDeliveryInProgressEventDTO buildOrderDeliveryInProgressEvent(Order order) {
            return new OrderDeliveryInProgressEventDTO(
                        UUID.randomUUID().toString(),
                        order.getOrderNumber(),
                        getOrderItems(order),
                        order.getCustomer(),
                        order.getDeliveryAddress(),
                        LocalDateTime.now());
      }

      public static OrderCancelledEventDTO buildOrderCancelledEvent(Order order, String reason) {
            return new OrderCancelledEventDTO(
                        UUID.randomUUID().toString(),
                        order.getOrderNumber(),
                        getOrderItems(order),
                        order.getCustomer(),
                        order.getDeliveryAddress(),
                        reason,
                        LocalDateTime.now());
      }

      public static OrderErrorEventDTO buildOrderErrorEvent(Order order, String reason) {
            return new OrderErrorEventDTO(
                        UUID.randomUUID().toString(),
                        order.getOrderNumber(),
                        getOrderItems(order),
                        order.getCustomer(),
                        order.getDeliveryAddress(),
                        reason,
                        LocalDateTime.now());
      }

      private static Set<OrderItemDTO> getOrderItems(Order order) {
            return order.getItems().stream()
                        .map(item -> new OrderItemDTO(item.getCode(), item.getName(), item.getPrice(),
                                    item.getQuantity()))
                        .collect(Collectors.toSet());
      }

}