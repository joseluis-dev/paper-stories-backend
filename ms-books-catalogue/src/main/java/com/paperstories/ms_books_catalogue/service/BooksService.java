package com.paperstories.ms_books_catalogue.service;

import java.sql.Date;
import java.util.List;

import com.paperstories.ms_books_catalogue.controller.model.BookDto;
import com.paperstories.ms_books_catalogue.controller.model.CreateBookRequest;
import com.paperstories.ms_books_catalogue.data.model.Book;

public interface BooksService {
    List<Book> getBooks(String title, String author, String category, Date releaseDate, String isbn, Float score, Boolean visible, Float price);
    Book getBookById(Long id);
    Book createBook(CreateBookRequest book);
    Book updateBook(Long bookID, String updateRequest);
    Book updateBook(Long bookID, BookDto book);
    Boolean deleteBook(Long id);
  
}
