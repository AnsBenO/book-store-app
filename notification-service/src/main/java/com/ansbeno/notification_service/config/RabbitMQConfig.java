package com.ansbeno.notification_service.config;

import com.ansbeno.notification_service.ApplicationProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
class RabbitMQConfig {
      private final ApplicationProperties properties;

      @Bean
      DirectExchange exchange() {
            return new DirectExchange(properties.orderEventsExchange());
      }

      @Bean
      Queue newOrdersQueue() {
            return QueueBuilder.durable(properties.newOrdersQueue()).build();
      }

      @Bean
      Binding newOrdersQueueBinding() {
            // ? Bind 'newOrdersQueue' to the exchange using the same name as the routing
            // ? key
            return BindingBuilder.bind(newOrdersQueue()).to(exchange()).with(properties.newOrdersQueue());
      }

      @Bean
      Queue deliveryInProgressQueue() {
            return QueueBuilder.durable(properties.deliveryInProgressQueue()).build();
      }

      @Bean
      Binding deliveryInProgressQueueBinding() {
            // ? Bind 'deliveryInProgressQueue' to the exchange using the same name as the
            // ? routing key
            return BindingBuilder.bind(deliveryInProgressQueue()).to(exchange())
                        .with(properties.deliveryInProgressQueue());
      }

      @Bean
      Queue deliveredOrdersQueue() {
            return QueueBuilder.durable(properties.deliveredOrdersQueue()).build();
      }

      @Bean
      Binding deliveredOrdersQueueBinding() {
            // ? Bind 'deliveredOrdersQueue' to the exchange using the same name as the
            // ? routing key
            return BindingBuilder.bind(deliveredOrdersQueue()).to(exchange()).with(properties.deliveredOrdersQueue());
      }

      @Bean
      Queue cancelledOrdersQueue() {
            return QueueBuilder.durable(properties.cancelledOrdersQueue()).build();
      }

      @Bean
      Binding cancelledOrdersQueueBinding() {
            // ? Bind 'cancelledOrdersQueue' to the exchange using the same name as the
            // ? routing key
            return BindingBuilder.bind(cancelledOrdersQueue()).to(exchange()).with(properties.cancelledOrdersQueue());
      }

      @Bean
      Queue errorOrdersQueue() {
            return QueueBuilder.durable(properties.errorOrdersQueue()).build();
      }

      @Bean
      Binding errorOrdersQueueBinding() {
            // ? Bind 'errorOrdersQueue' to the exchange using the same name as the routing
            // ? key
            return BindingBuilder.bind(errorOrdersQueue()).to(exchange()).with(properties.errorOrdersQueue());
      }

      @Bean
      @Autowired
      RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                  ObjectMapper objectMapper) {
            final var rabbitTemplate = new RabbitTemplate(connectionFactory);
            rabbitTemplate.setMessageConverter(jacksonConverter(objectMapper));
            return rabbitTemplate;
      }

      @Bean
      Jackson2JsonMessageConverter jacksonConverter(ObjectMapper mapper) {
            return new Jackson2JsonMessageConverter(mapper);
      }
}