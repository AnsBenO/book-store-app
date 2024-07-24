package com.ansbeno.order_service.domain.order;

import java.util.List;

import com.ansbeno.order_service.domain.exceptions.OrderNotFoundException;
import com.ansbeno.order_service.dto.OrderDTO;

public interface OrderService {

      // CreateOrderResponse createOrder(String userName, CreateOrderRequest request);

      List<OrderSummary> findOrders(String userName);

      OrderDTO findUserOrder(String userName, String orderNumber) throws OrderNotFoundException;

      void processNewOrders();

      void process(Order order);

      boolean canBeDelivered(Order order);
}
