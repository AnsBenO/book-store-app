package com.ansbeno.books_service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BookDto {

      private Long id;

      @NotEmpty(message = "Book code is required")
      private String code;

      @NotEmpty(message = "Book name is required")

      private String name;

      private String description;

      private String imageUrl;

      @NotNull(message = "Book price is required")
      @DecimalMin("0.1")
      private BigDecimal price;

      private LocalDateTime createdAt;

      private LocalDateTime updatedAt;
}