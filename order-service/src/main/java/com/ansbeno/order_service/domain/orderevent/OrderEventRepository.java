package com.ansbeno.order_service.domain.orderevent;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderEventRepository extends JpaRepository<OrderEvent, Long> {

}
