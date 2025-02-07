package com.paperstories.ms_books_payments.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanConfig {
  @Bean
  public ObjectMapper objectMapper() {
      return new ObjectMapper();
  }

  @Bean
  public RestTemplate restTemplate() {
      return new RestTemplate();
  }
}
