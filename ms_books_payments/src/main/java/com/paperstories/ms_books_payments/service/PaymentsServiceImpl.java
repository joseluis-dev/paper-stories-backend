package com.paperstories.ms_books_payments.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.paperstories.ms_books_payments.model.Book;
import com.paperstories.ms_books_payments.model.PaymentResponse;
import com.paperstories.ms_books_payments.model.Transaction;

@Service
public class PaymentsServiceImpl implements PaymentsService{
	
	@Autowired
    private RestTemplate restTemplate;

    private static final String CATALOGUE_URL = "http://localhost:8088/books/"; // Assuming ms-books-catalogue is running on port 8081

    @Override
    public boolean validateBook(Long bookId) {
    	try {
            Book book = restTemplate.getForObject(CATALOGUE_URL + bookId, Book.class);
            return book != null && book.getVisible();
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public PaymentResponse processPayment(Transaction transaction) {
    	try {
            Book book = restTemplate.getForObject(CATALOGUE_URL + transaction.getBookId(), Book.class);

            if (book == null || !book.getVisible()) {
                return new PaymentResponse(null, 0f, transaction.getUserId(), "Failed: Book not available");
            }

            Float totalPrice = transaction.getQuantity() * book.getPrecio();
            return new PaymentResponse(book.getTitulo(), totalPrice, transaction.getUserId(), "Success");

        } catch (Exception e) {
            return new PaymentResponse(null, 0f, transaction.getUserId(), "Failed: Error processing payment");
        }
    }
	
}
