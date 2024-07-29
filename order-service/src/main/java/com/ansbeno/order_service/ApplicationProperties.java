package com.ansbeno.order_service;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "orders")
public record ApplicationProperties(
            String orderEventsExchange,
            String newOrdersQueue,
            String deliveryInProgressQueue,
            String deliveredOrdersQueue,
            String cancelledOrdersQueue,
            String errorOrdersQueue,
            String publishOrderEventsJobCron,
            String newOrdersJobCron,
            String booksServiceUrl) {
}