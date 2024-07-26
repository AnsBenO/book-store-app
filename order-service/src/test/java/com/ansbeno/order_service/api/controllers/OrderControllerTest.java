package com.ansbeno.order_service.api.controllers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.ansbeno.order_service.AbstractIntegrationTest;
import com.ansbeno.order_service.testdata.TestDataFactory;

import io.restassured.http.ContentType;

public class OrderControllerTest extends AbstractIntegrationTest {

      @Nested
      /**
       * CreateOrderTest
       */
      class CreateOrderTest {
            @Test
            void shouldCreateOrderSuccessfully() {
                  var payload = """
                                                            {
                                  "customer" : {
                                      "name": "Anas",
                                      "email": "ans@email.com",
                                      "phone": "9999999973"
                                  },
                                  "deliveryAddress" : {
                                      "addressLine1": "Birkelweg",
                                      "addressLine2": "Hans-Edenhofer-Straße 23",
                                      "city": "Berlin",
                                      "state": "Berlin",
                                      "zipCode": "94258",
                                      "country": "Germany"
                                  },
                                  "items": [
                                      {
                                          "code": "P100",
                                          "name": "Product 1",
                                          "price": 25.50,
                                          "quantity": 1
                                      }
                                  ]
                              }
                                                            """;
                  given().contentType(ContentType.JSON)
                              // .header("Authorization", "Bearer " + getToken())
                              .body(payload)
                              .when()
                              .post("/api/orders")
                              .then()
                              .statusCode(HttpStatus.CREATED.value())
                              .body("orderNumber", notNullValue());
            }

            @Test
            void shouldReturnBadRequestWhenMandatoryDataIsMissing() {
                  var payload = TestDataFactory.createOrderRequestWithInvalidCustomer();
                  given().contentType(ContentType.JSON)
                              // .header("Authorization", "Bearer " + getToken())
                              .body(payload)
                              .when()
                              .post("/api/orders")
                              .then()
                              .statusCode(HttpStatus.BAD_REQUEST.value());
            }

      }
}
