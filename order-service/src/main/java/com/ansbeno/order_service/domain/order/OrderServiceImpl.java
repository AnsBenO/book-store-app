package com.ansbeno.order_service.domain.order;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ansbeno.order_service.domain.exceptions.OrderNotFoundException;
import com.ansbeno.order_service.dto.CreateOrderRequestDTO;
import com.ansbeno.order_service.dto.CreateOrderResponseDTO;
import com.ansbeno.order_service.dto.OrderDTO;
import com.ansbeno.order_service.mappers.OrderMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

      private final OrderRepository orderRepository;

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

      @Override
      public CreateOrderResponseDTO createNewOrder(String userName, CreateOrderRequestDTO request) {
            Order newOrder = OrderMapper.mapRequestToEntity(request);
            newOrder.setUsername(userName);
            Order savedOrder = orderRepository.save(newOrder); // InvocationTargetException thrown here
            log.info("Created Order with number {}", savedOrder.getOrderNumber());
            return new CreateOrderResponseDTO(savedOrder.getOrderNumber());
      }

}
