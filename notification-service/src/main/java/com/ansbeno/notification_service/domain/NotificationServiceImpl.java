package com.ansbeno.notification_service.domain;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.ansbeno.notification_service.ApplicationProperties;
import com.ansbeno.notification_service.dto.orderevent.OrderCancelledEventDTO;
import com.ansbeno.notification_service.dto.orderevent.OrderCreatedEventDTO;
import com.ansbeno.notification_service.dto.orderevent.OrderDeliveredEventDTO;
import com.ansbeno.notification_service.dto.orderevent.OrderDeliveryInProgressEventDTO;
import com.ansbeno.notification_service.dto.orderevent.OrderErrorEventDTO;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService {
      private final JavaMailSender emailSender;
      private final ApplicationProperties properties;

      @Override
      public void sendOrderCreatedNotification(OrderCreatedEventDTO event) {
            String message = """
                        ===================================================
                        Order Created Notification
                        ----------------------------------------------------
                        Dear %s,
                        Your order with orderNumber: %s has been created successfully.

                        Thanks,
                        BookStore Team
                        ===================================================
                        """
                        .formatted(event.getCustomer().name(), event.getOrderNumber());
            log.info("\n{}", message);
            sendEmail(event.getCustomer().email(), "Order Created Notification", message);
      }

      @Override
      public void sendOrderDeliveryInProgressNotification(OrderDeliveryInProgressEventDTO event) {
            String message = String.format(
                        """
                                    ===================================================
                                    Order Delivery In Progress Notification
                                    ----------------------------------------------------
                                    Dear %s,
                                    Your order with orderNumber: %s is currently in progress and will be delivered soon.

                                    Thanks,
                                    BookStore Team
                                    ===================================================
                                          """,
                        event.getCustomer().name(), event.getOrderNumber());
            log.info("\n{}", message);
            sendEmail(event.getCustomer().email(), "Order Delivery In Progress Notification", message);
      }

      @Override
      public void sendOrderDeliveredNotification(OrderDeliveredEventDTO event) {
            String message = """
                        ===================================================
                        Order Delivered Notification
                        ----------------------------------------------------
                        Dear %s,
                        Your order with orderNumber: %s has been delivered successfully.

                        Thanks,
                        BookStore Team
                        ===================================================
                        """
                        .formatted(event.getCustomer().name(), event.getOrderNumber());
            log.info("\n{}", message);
            sendEmail(event.getCustomer().email(), "Order Delivered Notification", message);
      }

      @Override
      public void sendOrderCancelledNotification(OrderCancelledEventDTO event) {
            String message = """
                        ===================================================
                        Order Cancelled Notification
                        ----------------------------------------------------
                        Dear %s,
                        Your order with orderNumber: %s has been cancelled.
                        Reason: %s

                        Thanks,
                        BookStore Team
                        ===================================================
                        """
                        .formatted(event.getCustomer().name(), event.getOrderNumber(), event.getReason());
            log.info("\n{}", message);
            sendEmail(event.getCustomer().email(), "Order Cancelled Notification", message);
      }

      @Override
      public void sendOrderErrorEventNotification(OrderErrorEventDTO event) {
            String message = """
                        ===================================================
                        Order Processing Failure Notification
                        ----------------------------------------------------
                        Hi %s,
                        The order processing failed for orderNumber: %s.
                        Reason: %s

                        Thanks,
                        BookStore Team
                        ===================================================
                        """
                        .formatted(properties.supportEmail(), event.getOrderNumber(), event.getReason());
            log.info("\n{}", message);
            sendEmail(properties.supportEmail(), "Order Processing Failure Notification", message);
      }

      private void sendEmail(String recipient, String subject, String content) {
            try {
                  MimeMessage mimeMessage = emailSender.createMimeMessage();
                  MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
                  helper.setFrom(properties.supportEmail());
                  helper.setTo(recipient);
                  helper.setSubject(subject);
                  helper.setText(content);
                  emailSender.send(mimeMessage);
                  log.info("Email sent to: {}", recipient);

            } catch (MessagingException e) {
                  log.error("Failed to send email to {}: {}", recipient, e.getMessage(), e);
                  throw new EmailSendingException("Failed to send email to " + recipient, e);
            }

      }

}
