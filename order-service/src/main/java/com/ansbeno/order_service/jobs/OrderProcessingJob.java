package com.ansbeno.order_service.jobs;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.Instant;

import com.ansbeno.order_service.domain.order.OrderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.LockAssert;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;

@RequiredArgsConstructor
@Slf4j
@Component
public class OrderProcessingJob {

      private final OrderService orderService;

      @Scheduled(cron = "${orders.new-orders-job-cron}")
      @SchedulerLock(name = "processNewOrders")
      public void processNewOrders() {
            LockAssert.assertLocked();
            log.info("Processing new orders at {}", Instant.now());
            orderService.processNewOrders();
      }
}
