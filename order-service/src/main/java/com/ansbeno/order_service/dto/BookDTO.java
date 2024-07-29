package com.ansbeno.order_service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BookDTO {

      private Long id;

      private String code;

      private String name;

      private String description;

      private String imageUrl;

      private BigDecimal price;

      private LocalDateTime createdAt;

      private LocalDateTime updatedAt;
}