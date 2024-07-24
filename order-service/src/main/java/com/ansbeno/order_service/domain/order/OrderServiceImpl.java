package com.ansbeno.order_service.domain.order;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ansbeno.order_service.domain.exceptions.OrderNotFoundException;
import com.ansbeno.order_service.dto.OrderDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

      private OrderRepository orderRepository;

      @Override
      public OrderDTO findUserOrder(String userName, String orderNumber) throws OrderNotFoundException {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'findUserOrder'");
      }

      @Override
      public void processNewOrders() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'processNewOrders'");
      }

      @Override
      public void process(Order order) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'process'");
      }

      @Override
      public boolean canBeDelivered(Order order) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'canBeDelivered'");
      }

      @Override
      public List<OrderSummary> findOrders(String userName) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'findOrders'");
      }

}
