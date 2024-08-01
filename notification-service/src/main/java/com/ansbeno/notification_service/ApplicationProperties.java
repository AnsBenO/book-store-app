package com.ansbeno.notification_service;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "notifications")
public record ApplicationProperties(
            String supportEmail,
            String orderEventsExchange,
            String newOrdersQueue,
            String deliveryInProgressQueue,
            String deliveredOrdersQueue,
            String cancelledOrdersQueue,
            String errorOrdersQueue,
            String booksServiceUrl) {
}