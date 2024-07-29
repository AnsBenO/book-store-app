package com.ansbeno.order_service.domain.orderevent;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ansbeno.order_service.domain.exceptions.InvalidOrderException;
import com.ansbeno.order_service.dto.orderevent.OrderCancelledEventDTO;
import com.ansbeno.order_service.dto.orderevent.OrderCreatedEventDTO;
import com.ansbeno.order_service.dto.orderevent.OrderDeliveredEventDTO;
import com.ansbeno.order_service.dto.orderevent.OrderDeliveryInProgressEventDTO;
import com.ansbeno.order_service.dto.orderevent.OrderErrorEventDTO;
import com.ansbeno.order_service.dto.orderevent.OrderEventDTO;
import com.ansbeno.order_service.mappers.OrderEventMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Transactional
@Slf4j
@Service
public class OrderEventServiceImpl implements OrderEventService {
      private final OrderEventRepository orderEventRepository;
      private final ObjectMapper objectMapper;
      private final OrderEventPublisher orderEventPublisher;

      @Override
      public void save(OrderEventDTO event) throws InvalidOrderException {
            OrderEvent orderEvent = new OrderEvent();
            try {

                  orderEvent = OrderEventMapper.toOrderEvent(event);
                  orderEventRepository.save(orderEvent);
            } catch (JsonProcessingException e) {
                  throw new InvalidOrderException("Invalid Order " + event.getOrderNumber());
            }
      }

      public void publishOrderEvents() {
            int batchSize = 100;
            Sort sort = Sort.by("createdAt").ascending();
            int page = 0;

            ExecutorService executorService = Executors.newFixedThreadPool(10);

            while (true) {
                  Pageable pageable = PageRequest.of(page, batchSize, sort);
                  List<OrderEvent> events = orderEventRepository.findAll(pageable).getContent();
                  log.info("Found {} Order Events to be published in batch {}", events.size(), page);

                  if (events.isEmpty()) {
                        break;
                  }

                  List<OrderEvent> finalEvents = events;
                  executorService.submit(() -> processEvents(finalEvents));
                  page++;
            }

            executorService.shutdown();
            try {
                  if (!executorService.awaitTermination(60, TimeUnit.MINUTES)) {
                        executorService.shutdownNow();
                  }
            } catch (InterruptedException e) {
                  executorService.shutdownNow();
                  Thread.currentThread().interrupt();
            }
      }

      private void processEvents(List<OrderEvent> events) {
            for (OrderEvent event : events) {
                  try {
                        this.publishEvent(event);
                        orderEventRepository.delete(event);
                  } catch (Exception e) {
                        log.error("Error publishing or deleting event: {}", event, e);
                  }
            }
      }

      private void publishEvent(OrderEvent event) {
            OrderEventType eventType = event.getEventType();
            switch (eventType) {
                  case ORDER_CREATED:
                        OrderCreatedEventDTO orderCreatedEvent = fromJsonPayload(event.getPayload(),
                                    OrderCreatedEventDTO.class);
                        orderEventPublisher.publish(orderCreatedEvent);
                        break;
                  case ORDER_DELIVERED:
                        OrderDeliveredEventDTO orderDeliveredEvent = fromJsonPayload(event.getPayload(),
                                    OrderDeliveredEventDTO.class);
                        orderEventPublisher.publish(orderDeliveredEvent);
                        break;
                  case ORDER_DELIVERY_IN_PROGRESS:
                        OrderDeliveryInProgressEventDTO orderDeliveryInProgressEvent = fromJsonPayload(
                                    event.getPayload(), OrderDeliveryInProgressEventDTO.class);
                        orderEventPublisher.publish(orderDeliveryInProgressEvent);
                        break;
                  case ORDER_CANCELLED:
                        OrderCancelledEventDTO orderCancelledEvent = fromJsonPayload(event.getPayload(),
                                    OrderCancelledEventDTO.class);
                        orderEventPublisher.publish(orderCancelledEvent);
                        break;
                  case ORDER_PROCESSING_FAILED:
                        OrderErrorEventDTO orderErrorEvent = fromJsonPayload(event.getPayload(),
                                    OrderErrorEventDTO.class);
                        orderEventPublisher.publish(orderErrorEvent);
                        break;
                  default:
                        log.warn("Unsupported OrderEventType: {}", eventType);
            }
      }

      private <T> T fromJsonPayload(String json, Class<T> type) throws InvalidOrderException {
            try {
                  return objectMapper.readValue(json, type);
            } catch (JsonProcessingException e) {
                  throw new InvalidOrderException("[ Invalid OrderEvent ] > > > " + e.getMessage());
            }
      }
}
