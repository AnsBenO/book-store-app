package com.ansbeno.order_service.domain.orderevent;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.ansbeno.order_service.ApplicationProperties;
import com.ansbeno.order_service.dto.orderevent.OrderCancelledEventDTO;
import com.ansbeno.order_service.dto.orderevent.OrderCreatedEventDTO;
import com.ansbeno.order_service.dto.orderevent.OrderDeliveredEventDTO;
import com.ansbeno.order_service.dto.orderevent.OrderDeliveryInProgressEventDTO;
import com.ansbeno.order_service.dto.orderevent.OrderErrorEventDTO;
import com.ansbeno.order_service.dto.orderevent.OrderEventDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class OrderEventPublisher {
      private final RabbitTemplate rabbitTemplate;
      private final ApplicationProperties properties;

      private void send(String routingKey, OrderEventDTO payload) {
            rabbitTemplate.convertAndSend(properties.orderEventsExchange(), routingKey, payload);
      }

      public void publish(OrderCreatedEventDTO event) {
            this.send(properties.newOrdersQueue(), event);
      }

      public void publish(OrderDeliveredEventDTO event) {
            this.send(properties.deliveredOrdersQueue(), event);
      }

      public void publish(OrderDeliveryInProgressEventDTO event) {
            this.send(properties.deliveryInProgressQueue(), event);
      }

      public void publish(OrderCancelledEventDTO event) {
            this.send(properties.cancelledOrdersQueue(), event);
      }

      public void publish(OrderErrorEventDTO event) {
            this.send(properties.errorOrdersQueue(), event);
      }

}
