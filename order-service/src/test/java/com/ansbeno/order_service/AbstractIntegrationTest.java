package com.ansbeno.order_service;

import java.math.BigDecimal;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import org.wiremock.integrations.testcontainers.WireMockContainer;

import com.github.tomakehurst.wiremock.client.WireMock;

import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Import(ContainersConfig.class)
@AutoConfigureMockMvc
public abstract class AbstractIntegrationTest {

      @LocalServerPort
      int port;

      @Autowired
      protected MockMvc mockMvc;

      static WireMockContainer wiremockServer = new WireMockContainer("wiremock/wiremock:3.5.2-alpine");

      @BeforeAll
      static void beforeAll() {
            wiremockServer.start();
            configureFor(wiremockServer.getHost(), wiremockServer.getPort());
      }

      @DynamicPropertySource
      static void configureProperties(DynamicPropertyRegistry registry) {
            registry.add("orders.books-service-url", wiremockServer::getBaseUrl);
      }

      @BeforeEach
      void setUp() {
            RestAssured.port = port;
      }

      protected static void mockGetBookByCode(String code, String name, BigDecimal price) {
            stubFor(WireMock.get(urlMatching("/api/books/" + code))
                        .willReturn(aResponse()
                                    .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                    .withStatus(200)
                                    .withBody(
                                                """
                                                                {
                                                                    "code": "%s",
                                                                    "name": "%s",
                                                                    "price": %f
                                                                }
                                                            """
                                                            .formatted(code, name, price.doubleValue()))));
      }

}
