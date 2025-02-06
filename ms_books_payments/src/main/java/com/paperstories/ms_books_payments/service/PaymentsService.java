package com.paperstories.ms_books_payments.service;
import com.paperstories.ms_books_payments.model.PaymentResponse;
import com.paperstories.ms_books_payments.model.Transaction;

public interface PaymentsService {
	boolean validateBook(Long bookId);
	PaymentResponse processPayment(Transaction transaction);
}
