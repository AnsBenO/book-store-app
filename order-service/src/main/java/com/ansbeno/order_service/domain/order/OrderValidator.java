package com.ansbeno.order_service.domain.order;

import java.util.Set;

import org.springframework.stereotype.Component;

import com.ansbeno.order_service.clients.BooksServiceClient;
import com.ansbeno.order_service.domain.exceptions.InvalidOrderException;
import com.ansbeno.order_service.dto.BookDTO;
import com.ansbeno.order_service.dto.CreateOrderRequestDTO;
import com.ansbeno.order_service.dto.OrderItemDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
class OrderValidator {

      private final BooksServiceClient client;

      void validate(CreateOrderRequestDTO request) {
            Set<OrderItemDTO> items = request.items();
            for (OrderItemDTO item : items) {
                  BookDTO book = client.getBookByCode(item.code())
                              .orElseThrow(() -> new RuntimeException(
                                          "Error encountered while fetching Book with code:" + item.code()));
                  if (item.price().compareTo(book.getPrice()) != 0) {
                        log.error(
                                    "Book price not matching. Actual price:{}, received price:{}",
                                    book.getPrice(),
                                    item.price());
                        throw new InvalidOrderException("Book price not matching");
                  }
            }
      }
}
