package com.ansbeno.order_service.clients;

import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import java.time.Duration;
import com.ansbeno.order_service.ApplicationProperties;

@Configuration
public class BooksServiceClientConfig {
      @Bean
      RestClient restClient(RestClient.Builder builder, ApplicationProperties properties) {
            return builder.baseUrl(properties.booksServiceUrl())
                        .requestFactory(ClientHttpRequestFactories.get(ClientHttpRequestFactorySettings.DEFAULTS
                                    .withConnectTimeout(Duration.ofSeconds(5))
                                    .withReadTimeout(Duration.ofSeconds(5))))
                        .build();
      }
}
