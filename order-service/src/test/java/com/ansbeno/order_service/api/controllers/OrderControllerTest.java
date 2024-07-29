package com.ansbeno.order_service.api.controllers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.assertj.core.api.Assertions.assertThat;
import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import com.ansbeno.order_service.AbstractIntegrationTest;
import com.ansbeno.order_service.domain.order.OrderSummary;
import com.ansbeno.order_service.testdata.TestDataFactory;

import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;

@Sql("/test-orders.sql")
public class OrderControllerTest extends AbstractIntegrationTest {

    @Nested
    /**
     * CreateOrderTest
     */
    class CreateOrderTest {
        @Test
        void shouldCreateOrderSuccessfully() {
            mockGetBookByCode("P100", "The Hunger Games", new BigDecimal("34.0"));
            var payload = """
                    {
                        "customer": {
                            "name": "Hello Geoe",
                            "email": "ans@email.com",
                            "phone": "999999999"
                        },
                        "deliveryAddress": {
                            "addressLine1": "Tetuan",
                            "addressLine2": "MA 23",
                            "city": "Tetuan",
                            "state": "MA",
                            "zipCode": "9019",
                            "country": "USA"
                        },
                        "items": [
                            {
                                "code": "P100",
                                "name": "The Hunger Games",
                                "price": 34.0,
                                "quantity": 1
                            }
                        ]
                    }
                                                                                          """;
            given().contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .post("/api/orders")
                    .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .body("orderNumber", notNullValue()); // test completed successfully
        }

        @Test
        void shouldReturnBadRequestWhenMandatoryDataIsMissing() {
            var payload = TestDataFactory.createOrderRequestWithInvalidDeliveryAddress();
            given().contentType(ContentType.JSON)

                    .body(payload)
                    .when()
                    .post("/api/orders")
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value()); // test completed successfully
        }

    }

    @Nested
    class GetOrdersTests {
        @Test
        @Sql("/test-orders.sql")
        void shouldGetOrdersSuccessfully() {
            List<OrderSummary> orderSummaries = given().when()
                    .get("/api/orders")
                    .then()
                    .statusCode(200)
                    .extract()
                    .body()
                    .as(new TypeRef<>() {
                    });

            assertThat(orderSummaries).hasSize(2);
            // RESULTS: expected 3 but was 0
        }
    }

    @Nested
    class GetOrderByOrderNumberTest {
        String orderNumber = "order-123";

        @Test
        @Sql("/test-orders.sql")
        void shouldGetOrderSuccessfully() {
            given()
                    .when()
                    .get("/api/orders/{orderNumber}", orderNumber)
                    .then()
                    .statusCode(200) // RESULTS: I get 404 here
                    .body("orderNumber", is(orderNumber))
                    .body("items.size()", is(2));
        }
    }
}