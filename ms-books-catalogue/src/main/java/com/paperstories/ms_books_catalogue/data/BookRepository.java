package com.paperstories.ms_books_catalogue.data;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.paperstories.ms_books_catalogue.data.model.Book;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BookRepository {
  private final BookJpaRepository  repository;

  public List<Book> getBooks() {
    return repository.findAll();
  }

  public Book getById(Long id) {
    return repository.findById(id).orElse(null);
  }

  public Book save(Book book) {
    return repository.save(book);
  }

  public void delete(Book book) {
    repository.delete(book);
  }

  // public List<Book> search(String titulo, String autor, String categoria, Date fechaPublicacion, String isbn, Float valoracion, Boolean visible, Float precio) {
  //   Usar el searchCriteria del código de apoyo: https://github.com/UnirCs/back-end-inventory-products/blob/master/src/main/java/com/unir/products/data/ProductRepository.java
  //   SearchCriteria<Product> spec = new SearchCriteria<>();
  //   return repository.findAll(spec);
  // }
}
