package com.ansbeno.order_service.clients;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.ansbeno.order_service.dto.BookDTO;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class BooksServiceClient {

      private final RestClient restClient;

      @CircuitBreaker(name = "books-service")
      @Retry(name = "books-service", fallbackMethod = "getBookByCodeFallback")
      public Optional<BookDTO> getBookByCode(String code) {
            log.info("Fetching book for code: {}", code);
            var book = restClient.get().uri("/api/books/{code}", code)
                        .retrieve()
                        .body(BookDTO.class);
            return Optional.ofNullable(book);
      }

      Optional<BookDTO> getBookByCodeFallback(String code, Throwable t) {
            log.info("catalog-service get book by code fallback: code:{}, Error: {} ", code, t.getMessage());
            return Optional.empty();
      }
}