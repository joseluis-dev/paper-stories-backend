package com.paperstories.ms_books_payments.facade;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.paperstories.ms_books_payments.facade.model.Book;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class BooksFacade {
  
  @Value("${getBook.url}")
  private String getBookUrl;

  private final RestTemplate restTemplate;

  public Book getBook(String id) {
    log.info("Getting book with id: {}", id);
    try {
      String url = String.format(getBookUrl, id);
      log.info("Getting book with ID {}. Request to {}", id, url);
      return restTemplate.getForObject(url, Book.class);
    } catch (HttpClientErrorException e) {
      log.error("Client Error: {}, Book with ID {}", e.getStatusCode(), id);
      return null;
    } catch (HttpServerErrorException e) {
      log.error("Server Error: {}, Book with ID {}", e.getStatusCode(), id);
      return null;
    } catch (Exception e) {
      log.error("Error: {}, Book with ID {}", e.getMessage(), id);
      return null;
    }
  }
}
