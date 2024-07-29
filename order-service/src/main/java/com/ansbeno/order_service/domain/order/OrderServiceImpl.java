package com.ansbeno.order_service.domain.order;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ansbeno.order_service.domain.exceptions.OrderNotFoundException;
import com.ansbeno.order_service.domain.orderevent.OrderEventService;
import com.ansbeno.order_service.dto.CreateOrderRequestDTO;
import com.ansbeno.order_service.dto.CreateOrderResponseDTO;
import com.ansbeno.order_service.dto.OrderDTO;
import com.ansbeno.order_service.dto.orderevent.OrderCreatedEventDTO;
import com.ansbeno.order_service.mappers.OrderEventMapper;
import com.ansbeno.order_service.mappers.OrderMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

      private final OrderRepository orderRepository;
      private final OrderEventService orderEventService;
      private final OrderValidator orderValidator;

      private static final List<String> DELIVERY_ALLOWED_COUNTRIES = List.of("USA", "GERMANY", "UK");

      @Override
      public OrderDTO findUserOrder(String userName, String orderNumber) throws OrderNotFoundException {
            Optional<Order> orderOptional = orderRepository.findByUsernameAndOrderNumber(userName, orderNumber);
            if (orderOptional.isPresent()) {
                  Order order = orderOptional.get();
                  return OrderMapper.mapToOrderDTO(order);
            }
            throw new OrderNotFoundException(orderNumber);
      }

      @Override
      public void processNewOrders() {
            List<Order> orders = orderRepository.findByStatus(OrderStatus.NEW);
            log.info("Found {} new orders to process", orders.size());
            for (Order order : orders) {
                  this.process(order);
            }
      }

      private void process(Order order) {
            try {
                  if (canBeDelivered(order)) {
                        log.info("OrderNumber: {} can be delivered", order.getOrderNumber());
                        orderRepository.updateOrderStatus(order.getOrderNumber(), OrderStatus.DELIVERY_IN_PROGRESS);
                        orderEventService.save(OrderEventMapper.buildOrderDeliveryInProgressEvent(order));

                  } else {
                        log.info("OrderNumber: {} can not be delivered", order.getOrderNumber());
                        orderRepository.updateOrderStatus(order.getOrderNumber(), OrderStatus.CANCELLED);
                        orderEventService.save(
                                    OrderEventMapper.buildOrderCancelledEvent(order, "Can't deliver to the location"));
                  }
            } catch (RuntimeException e) {
                  log.error("Failed to process Order with orderNumber: {}", order.getOrderNumber(), e);
                  orderRepository.updateOrderStatus(order.getOrderNumber(), OrderStatus.ERROR);
                  orderEventService.save(OrderEventMapper.buildOrderErrorEvent(order, e.getMessage()));
            }
      }

      private boolean canBeDelivered(Order order) {
            return DELIVERY_ALLOWED_COUNTRIES.contains(
                        order.getDeliveryAddress().country().toUpperCase());
      }

      @Override
      public List<OrderSummary> findOrders(String username) {
            return orderRepository.findByUsername(username);
      }

      @Override
      public CreateOrderResponseDTO createNewOrder(String userName, CreateOrderRequestDTO request) {
            orderValidator.validate(request);
            Order newOrder = OrderMapper.mapRequestToEntity(request);
            newOrder.setUsername(userName);
            Order savedOrder = orderRepository.save(newOrder);
            log.info("Created Order with number {}", savedOrder.getOrderNumber());
            OrderCreatedEventDTO orderCreatedEvent = OrderEventMapper.buildOrderCreatedEvent(savedOrder);
            orderEventService.save(orderCreatedEvent);
            return new CreateOrderResponseDTO(savedOrder.getOrderNumber());
      }

}
