package com.ansbeno.notification_service.dto.order;

public record Address(
            String addressLine1,
            String addressLine2,
            String city,
            String state,
            String zipCode,
            String country) {

}