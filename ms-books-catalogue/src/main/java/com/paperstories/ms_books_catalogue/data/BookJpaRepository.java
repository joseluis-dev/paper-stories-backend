package com.paperstories.ms_books_catalogue.data;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.paperstories.ms_books_catalogue.data.model.Book;

interface BookJpaRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

  List<Book> findByTitulo(String titulo);
  List<Book> findByAutor(String autor);
  List<Book> findByCategoria(String categoria);
  List<Book> findByIsbn(String isbn);
  List<Book> findByValoracion(Float valoracion);
  List<Book> findByvisible(Boolean visible);
  List<Book> findByPrecio(Float precio);
  List<Book> findByFechaPublicacion(Date fechaPublicacion);
  
}
