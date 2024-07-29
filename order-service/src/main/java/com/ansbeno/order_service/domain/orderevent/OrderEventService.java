package com.ansbeno.order_service.domain.orderevent;

import com.ansbeno.order_service.domain.exceptions.InvalidOrderException;
import com.ansbeno.order_service.dto.orderevent.OrderEventDTO;

public interface OrderEventService {

      void save(OrderEventDTO event) throws InvalidOrderException;

      void publishOrderEvents();

}
