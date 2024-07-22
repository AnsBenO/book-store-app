package com.ansbeno.books_service.com.ansbeno.books_service.api.controllers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import com.ansbeno.books_service.AbstractIntegrationTest;

import io.restassured.http.ContentType;

@Sql("/test-data.sql")
class BookControllerTest extends AbstractIntegrationTest {
      @Test()
      void shouldReturnBooks() {
            given().contentType(ContentType.JSON)
                        .when()
                        .get("/api/books")
                        .then()
                        .statusCode(200)
                        .body("data", hasSize(10))
                        .body("totalElements", is(15))
                        .body("pageNumber", is(1))
                        .body("totalPages", is(2))
                        .body("isFirst", is(true))
                        .body("isLast", is(false))
                        .body("hasNext", is(true))
                        .body("hasPrevious", is(false));
      }
}
