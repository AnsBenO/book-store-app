package com.ansbeno.order_service.jobs;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.Instant;
import com.ansbeno.order_service.domain.orderevent.OrderEventService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.LockAssert;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;

@RequiredArgsConstructor
@Slf4j
@Component
public class OrderEventPublishimgJob {
      private final OrderEventService orderEventService;

      @Scheduled(cron = "${orders.publish-order-events-job-cron}")
      @SchedulerLock(name = "publishOrderEvents")
      public void publishOrderEvents() {
            LockAssert.assertLocked();
            log.info("Publishing Order Events at {}", Instant.now());
            orderEventService.publishOrderEvents();
      }
}
