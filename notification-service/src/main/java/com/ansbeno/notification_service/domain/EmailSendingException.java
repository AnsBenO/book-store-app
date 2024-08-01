package com.ansbeno.notification_service.domain;

public class EmailSendingException extends RuntimeException {
      public EmailSendingException(String message, Throwable cause) {
            super(message, cause);
      }
}