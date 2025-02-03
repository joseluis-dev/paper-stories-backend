package com.paperstories.ms_books_catalogue.controller;

import java.sql.Date;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paperstories.ms_books_catalogue.controller.model.BookDto;
import com.paperstories.ms_books_catalogue.controller.model.CreateBookRequest;
import com.paperstories.ms_books_catalogue.data.model.Book;
import com.paperstories.ms_books_catalogue.service.BooksService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BooksController {
  private final BooksService booksService;

  @GetMapping("/books")
  public ResponseEntity<List<Book>> getBooks(
    @RequestHeader Map<String, String> headers,
    @RequestParam(required = false) String titulo,
    @RequestParam(required = false) String autor,
    @RequestParam(required = false) String categoria,
    @RequestParam(required = false) Date fechaPublicacion,
    @RequestParam(required = false) String isbn,
    @RequestParam(required = false) Float valoracion,
    @RequestParam(required = false) Boolean visible,
    @RequestParam(required = false) Float precio) {

      log.info("headers: {}", headers);
    List<Book> books = booksService.getBooks(titulo, autor, categoria, fechaPublicacion, isbn, valoracion, visible, precio);
    return books != null ? ResponseEntity.ok(books) : ResponseEntity.ok(Collections.emptyList());
  }

  @GetMapping("/books/{id}")
  public ResponseEntity<Book> getBookById(@RequestHeader Map<String, String> headers, @PathVariable Long id) {
    log.info("headers: {}", headers);
    Book book = booksService.getBookById(id);
    return book != null ? ResponseEntity.ok(book) : ResponseEntity.notFound().build();
  }

  @DeleteMapping("/books/{id}")
  public ResponseEntity<Boolean> deleteBook(@RequestHeader Map<String, String> headers, @PathVariable Long id) {
    log.info("headers: {}", headers);
    Boolean deleted = booksService.deleteBook(id);
    return Boolean.TRUE.equals(deleted) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
  }

  @PostMapping("/books")
  public ResponseEntity<Book> addBook(@RequestHeader Map<String, String> headers, @RequestBody CreateBookRequest request) {
    log.info("headers: {}", headers);
    Book createdBook = booksService.createBook(request);
    return createdBook != null ? ResponseEntity.status(HttpStatus.CREATED).body(createdBook) : ResponseEntity.badRequest().build();
  }

  @PatchMapping("/books/{id}")
  public ResponseEntity<Book> patchBook(@RequestHeader Map<String, String> headers, @PathVariable Long id, @RequestBody String patchBody) {
    log.info("headers: {}", headers);
    Book updatedBook = booksService.updateBook(id, patchBody);
    return updatedBook != null ? ResponseEntity.ok(updatedBook) : ResponseEntity.notFound().build();
  }

  @PutMapping("/books/{id}")
  public ResponseEntity<Book> updateProduct(@RequestHeader Map<String, String> headers, @PathVariable Long id, @RequestBody BookDto request) {
    log.info("headers: {}", headers);
    Book updatedBook = booksService.updateBook(id, request);
    return updatedBook != null ? ResponseEntity.ok(updatedBook) : ResponseEntity.notFound().build();
  }
}
