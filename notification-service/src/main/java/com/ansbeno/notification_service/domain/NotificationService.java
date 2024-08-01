package com.ansbeno.notification_service.domain;

import com.ansbeno.notification_service.dto.orderevent.OrderCancelledEventDTO;
import com.ansbeno.notification_service.dto.orderevent.OrderCreatedEventDTO;
import com.ansbeno.notification_service.dto.orderevent.OrderDeliveredEventDTO;
import com.ansbeno.notification_service.dto.orderevent.OrderDeliveryInProgressEventDTO;
import com.ansbeno.notification_service.dto.orderevent.OrderErrorEventDTO;

public interface NotificationService {

      public void sendOrderCreatedNotification(OrderCreatedEventDTO event);

      void sendOrderDeliveredNotification(OrderDeliveredEventDTO event);

      void sendOrderDeliveryInProgressNotification(OrderDeliveryInProgressEventDTO event);

      void sendOrderCancelledNotification(OrderCancelledEventDTO event);

      void sendOrderErrorEventNotification(OrderErrorEventDTO event);

}
