package com.ansbeno.order_service.domain.order;

import java.util.List;

import com.ansbeno.order_service.domain.exceptions.OrderNotFoundException;
import com.ansbeno.order_service.dto.CreateOrderRequestDTO;
import com.ansbeno.order_service.dto.CreateOrderResponseDTO;
import com.ansbeno.order_service.dto.OrderDTO;

public interface OrderService {

      CreateOrderResponseDTO createNewOrder(String userName, CreateOrderRequestDTO request);

      List<OrderSummary> findOrders(String userName);

      OrderDTO findUserOrder(String userName, String orderNumber) throws OrderNotFoundException;

      void processNewOrders();

      void process(Order order);

      boolean canBeDelivered(Order order);
}
