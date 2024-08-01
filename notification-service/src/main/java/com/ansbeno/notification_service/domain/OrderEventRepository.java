package com.ansbeno.notification_service.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderEventRepository extends JpaRepository<OrderEvent, Long> {
      boolean existsByEventId(String eventId);
}
