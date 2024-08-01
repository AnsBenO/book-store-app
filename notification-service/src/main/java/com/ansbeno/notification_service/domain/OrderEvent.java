package com.ansbeno.notification_service.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "order_events")
public class OrderEvent {
      @Id
      @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_event_id_generator")
      @SequenceGenerator(name = "order_event_id_generator", sequenceName = "order_event_id_seq")
      private Long id;

      @Column(nullable = false, unique = true)
      private String eventId;

      @Builder.Default
      @Column(name = "created_at", nullable = false, updatable = false)
      private LocalDateTime createdAt = LocalDateTime.now();

      @Column(name = "updated_at")
      private LocalDateTime updatedAt;

      public OrderEvent(String eventId, LocalDateTime createdAt) {
            this.eventId = eventId;
            this.createdAt = createdAt;
      }
}
