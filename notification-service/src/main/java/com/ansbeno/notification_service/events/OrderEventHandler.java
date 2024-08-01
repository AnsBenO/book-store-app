package com.ansbeno.notification_service.events;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.ansbeno.notification_service.domain.NotificationService;
import com.ansbeno.notification_service.domain.OrderEvent;
import com.ansbeno.notification_service.domain.OrderEventRepository;
import com.ansbeno.notification_service.dto.orderevent.OrderCancelledEventDTO;
import com.ansbeno.notification_service.dto.orderevent.OrderCreatedEventDTO;
import com.ansbeno.notification_service.dto.orderevent.OrderDeliveredEventDTO;
import com.ansbeno.notification_service.dto.orderevent.OrderDeliveryInProgressEventDTO;
import com.ansbeno.notification_service.dto.orderevent.OrderErrorEventDTO;
import com.ansbeno.notification_service.dto.orderevent.OrderEventDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class OrderEventHandler {

      private final NotificationService notificationService;
      private final OrderEventRepository orderEventRepository;

      @RabbitListener(queues = "${notifications.new-orders-queue}")
      public void handle(OrderCreatedEventDTO event) {
            processEvent(event, () -> notificationService.sendOrderCreatedNotification(event));
      }

      @RabbitListener(queues = "${notifications.delivery-in-progress-queue}")
      public void handle(OrderDeliveryInProgressEventDTO event) {
            processEvent(event, () -> notificationService.sendOrderDeliveryInProgressNotification(event));
      }

      @RabbitListener(queues = "${notifications.delivered-orders-queue}")
      public void handle(OrderDeliveredEventDTO event) {
            processEvent(event, () -> notificationService.sendOrderDeliveredNotification(event));
      }

      @RabbitListener(queues = "${notifications.cancelled-orders-queue}")
      public void handle(OrderCancelledEventDTO event) {
            processEvent(event, () -> notificationService.sendOrderCancelledNotification(event));
      }

      @RabbitListener(queues = "${notifications.error-orders-queue}")
      public void handle(OrderErrorEventDTO event) {
            processEvent(event, () -> notificationService.sendOrderErrorEventNotification(event));
      }

      private <T extends OrderEventDTO> void processEvent(T event, Runnable notificationAction) {
            if (orderEventRepository.existsByEventId(event.getEventId())) {
                  log.warn("Received duplicate event with eventId: {}", event.getEventId());
                  return;
            }
            log.info("{} Received with orderNumber: {} ", event.getEventType(), event.getOrderNumber());
            notificationAction.run();
            var orderEvent = new OrderEvent(event.getEventId(), event.getCreatedAt());
            orderEventRepository.save(orderEvent);
      }

}
