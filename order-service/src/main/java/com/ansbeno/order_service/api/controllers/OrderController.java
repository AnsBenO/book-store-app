package com.ansbeno.order_service.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ansbeno.order_service.domain.order.OrderService;
import com.ansbeno.order_service.dto.CreateOrderRequestDTO;
import com.ansbeno.order_service.dto.CreateOrderResponseDTO;
import com.ansbeno.order_service.security.SecurityService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
@Slf4j
class OrderController {
      private final SecurityService securityService;
      private final OrderService orderService;

      @PostMapping
      @ResponseStatus(HttpStatus.CREATED)
      CreateOrderResponseDTO createNewOrder(@Valid @RequestBody CreateOrderRequestDTO request) {
            String username = securityService.getLoginUsername();
            log.info("Creating Order for User {}", username);
            return orderService.createNewOrder(username, request);
      }

}
