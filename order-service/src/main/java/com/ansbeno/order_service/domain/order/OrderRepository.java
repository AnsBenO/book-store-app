package com.ansbeno.order_service.domain.order;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

      List<Order> findByStatus(OrderStatus status);

      Optional<Order> findByOrderNumber(String orderNumber);

      default void updateOrderStatus(String orderNumber, OrderStatus status) {
            Order order = this.findByOrderNumber(orderNumber).orElseThrow(); // java.util.NoSuchElementException.NoSuchElementException

            order.setStatus(status);
            this.save(order);
      }

      @Query("""
                        SELECT NEW com.ansbeno.order_service.domain.order.OrderSummary(o.orderNumber, o.status)
                        FROM Order o
                        WHERE o.userName = :username
                  """)
      List<OrderSummary> findByUsername(String username);

      @Query("""
                        SELECT DISTINCT o
                        FROM Order o LEFT JOIN FETCH o.items
                        WHERE o.username = :username AND o.orderNumber = :orderNumber
                  """)
      Optional<Order> findByUsernameAndOrderNumber(String username, String orderNumber);

}
