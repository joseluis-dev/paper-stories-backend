package com.paperstories.ms_books_catalogue.data;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.apache.commons.lang.StringUtils;

import com.paperstories.ms_books_catalogue.data.model.Book;
import com.paperstories.ms_books_catalogue.data.utils.Consts;
import com.paperstories.ms_books_catalogue.data.utils.SearchOperation;
import com.paperstories.ms_books_catalogue.data.utils.SearchStatement;
import com.paperstories.ms_books_catalogue.data.utils.SearchCriteria;

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

  public List<Book> search(String title, String author, String category, Date releaseDate, String isbn, Float score, Boolean visible, Float price) {
    SearchCriteria<Book> spec = new SearchCriteria<>();

    if (StringUtils.isNotBlank(title)) {
      spec.add(new SearchStatement(Consts.TITULO, title, SearchOperation.MATCH));
    }

    if (StringUtils.isNotBlank(author)) {
      spec.add(new SearchStatement(Consts.AUTOR, author, SearchOperation.MATCH));
    }

    if (StringUtils.isNotBlank(category)) {
      spec.add(new SearchStatement(Consts.CATEGORIA, title, SearchOperation.EQUAL));
    }

    if (StringUtils.isNotBlank(isbn)) {
      spec.add(new SearchStatement(Consts.ISBN, isbn, SearchOperation.EQUAL));
    }

    if (releaseDate != null) {
      spec.add(new SearchStatement(Consts.FECHAPUBLICACION, releaseDate, SearchOperation.EQUAL));
    }

    if (score != null) {
      spec.add(new SearchStatement(Consts.VALORACION, score, SearchOperation.EQUAL));
    }

    if (visible != null) {
      spec.add(new SearchStatement(Consts.VISIBLE, visible, SearchOperation.EQUAL));
    }

    if (price != null) {
      spec.add(new SearchStatement(Consts.PRECIO, price, SearchOperation.EQUAL));
    }

    return repository.findAll(spec);
  }
}
