package com.ansbeno.books_service.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ansbeno.books_service.domain.book.BookService;
import com.ansbeno.books_service.dto.BookDto;
import com.ansbeno.books_service.dto.PagedResultDto;

import jakarta.validation.Valid;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/books")
class BookController {
      private final BookService bookService;

      @GetMapping
      ResponseEntity<PagedResultDto<BookDto>> getAllBooks(
                  @RequestParam(name = "page", defaultValue = "1") int page) {
            PagedResultDto<BookDto> books = bookService.findAll(page);
            return new ResponseEntity<>(books, HttpStatus.OK);
      }

      @PostMapping
      ResponseEntity<BookDto> createBook(@Valid @RequestBody BookDto bookDto) {
            BookDto savedBook = bookService.save(bookDto);
            return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
      }

      @GetMapping("/{code}")
      ResponseEntity<BookDto> getBookByCode(@PathVariable String code) {
            try {
                  BookDto book = bookService.findByCode(code);
                  return new ResponseEntity<>(book, HttpStatus.OK);
            } catch (NotFoundException e) {
                  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
      }

      @GetMapping("/id/{id}")
      ResponseEntity<BookDto> getBookById(@PathVariable Long id) {
            try {
                  BookDto book = bookService.findById(id);
                  return new ResponseEntity<>(book, HttpStatus.OK);
            } catch (NotFoundException e) {
                  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
      }

      @PutMapping("/{code}")
      ResponseEntity<BookDto> updateBook(@PathVariable String code, @Valid @RequestBody BookDto bookDto) {
            try {
                  bookService.update(bookDto);
                  return new ResponseEntity<>(bookDto, HttpStatus.OK);
            } catch (NotFoundException e) {
                  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
      }

      @DeleteMapping("/{code}")
      ResponseEntity<Void> deleteBookByCode(@PathVariable String code) {
            bookService.deleteByCode(code);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
}
