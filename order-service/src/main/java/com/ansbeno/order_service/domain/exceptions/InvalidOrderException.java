package com.ansbeno.order_service.domain.exceptions;

public class InvalidOrderException extends RuntimeException {

      public InvalidOrderException(String message) {
            super(message);
      }
}