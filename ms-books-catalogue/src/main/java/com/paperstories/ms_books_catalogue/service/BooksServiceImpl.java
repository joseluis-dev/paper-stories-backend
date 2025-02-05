package com.paperstories.ms_books_catalogue.service;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import com.paperstories.ms_books_catalogue.controller.model.BookDto;
import com.paperstories.ms_books_catalogue.controller.model.CreateBookRequest;
import com.paperstories.ms_books_catalogue.data.BookRepository;
import com.paperstories.ms_books_catalogue.data.model.Book;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BooksServiceImpl implements BooksService {
  private final BookRepository repository;
  private final ObjectMapper objectMapper;

  @Override
	public List<Book> getBooks(String title, String author, String category, Date releaseDate, String isbn, Float score, Boolean visible, Float price) {

		if (StringUtils.hasLength(title)
				|| StringUtils.hasLength(author)
				|| StringUtils.hasLength(category)
				|| StringUtils.hasLength(isbn)
				|| releaseDate != null
				|| score != null
				|| visible != null
				|| price != null) {
			return repository.search(title, author, category, releaseDate, isbn, score, visible, price);
		}

		List<Book> books = repository.getBooks();
		return books.isEmpty() ? null : books;
	}

	@Override
	public Book getBookById(Long id) {
		return repository.getById(Long.valueOf(id));
	}

	@Override
	public Boolean deleteBook(Long bookId) {

		Book book = repository.getById(Long.valueOf(bookId));

		if (book != null) {
			repository.delete(book);
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	@Override
	public Book createBook(CreateBookRequest request) {

		if (request != null && StringUtils.hasLength(request.getTitulo().trim())
				&& StringUtils.hasLength(request.getAutor().trim())
        && StringUtils.hasLength(request.getFechaPublicacion().toString().trim()) 
        && StringUtils.hasLength(request.getCategoria().trim())
        && StringUtils.hasLength(request.getIsbn().trim())
        && StringUtils.hasLength(request.getValoracion().toString().trim())
        && request.getVisible() != null
        && StringUtils.hasLength(request.getPrecio().toString().trim())) {

			Book book = Book.builder().titulo(request.getTitulo()).autor(request.getAutor()).fechaPublicacion(request.getFechaPublicacion()).categoria(request.getCategoria()).isbn(request.getIsbn()).valoracion(request.getValoracion()).visible(request.getVisible()).precio(request.getPrecio()).build();

			return repository.save(book);
		} else {
			return null;
		}
	}

	@Override
	public Book updateBook(Long bookId, String request) {

		//PATCH se implementa en este caso mediante Merge Patch: https://datatracker.ietf.org/doc/html/rfc7386
		Book book = repository.getById(Long.valueOf(bookId));
		if (book != null) {
			try {
				JsonMergePatch jsonMergePatch = JsonMergePatch.fromJson(objectMapper.readTree(request));
				JsonNode target = jsonMergePatch.apply(objectMapper.readTree(objectMapper.writeValueAsString(book)));
				Book patched = objectMapper.treeToValue(target, Book.class);
				repository.save(patched);
				return patched;
			} catch (JsonProcessingException | JsonPatchException e) {
				log.error("Error updating book {}", bookId, e);
                return null;
            }
        } else {
			return null;
		}
	}

	@Override
	public Book updateBook(Long bookId, BookDto updateRequest) {
		Book book = repository.getById(Long.valueOf(bookId));
		if (book != null) {
      book.update(updateRequest);
			repository.save(book);
			return book;
		} else {
			return null;
		}
	}
}
