package com.ansbeno.books_service.com.ansbeno.books_service.domain.book;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import com.ansbeno.books_service.entities.BookEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.ansbeno.books_service.domain.book.BookRepository;

@DataJpaTest(properties = {
            "spring.test.database.replace=none",
            "spring.datasource.url=jdbc:tc:postgresql:16-alpine:///db"
})
class BookRepositoryTest {
      @Autowired
      private BookRepository bookRepository;

      @Test
      void shouldGetAllBooks() {
            List<BookEntity> books = bookRepository.findAll();
            assertThat(books).hasSize(15);
      }

      @Test
      void shouldGetBookByCode() {
            BookEntity book = bookRepository.findByCode("B100").orElseThrow();
            assertThat(book.getCode()).isEqualTo("B100");
            assertThat(book.getName()).isEqualTo("The Hunger Games");
            assertThat(book.getDescription()).isEqualTo("Winning will make you famous. Losing means certain death...");
            assertThat(book.getPrice()).isEqualTo(new BigDecimal("34.0"));
      }

      @Test
      void shouldReturnEmptyWhenBookCodeNotExists() {
            assertThat(bookRepository.findByCode("invalid_book_code")).isEmpty();
      }
}
