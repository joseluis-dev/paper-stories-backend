package com.paperstories.ms_books_payments.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.paperstories.ms_books_payments.model.PaymentResponse;
import com.paperstories.ms_books_payments.model.Transaction;
import com.paperstories.ms_books_payments.service.PaymentsService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/payments")
public class PaymentsController {
	@Autowired
    private PaymentsService paymentService;

	@PostMapping("/purchase")
	public ResponseEntity<PaymentResponse> purchaseBook(@RequestBody Transaction transaction) {
	    log.info("Processing purchase for book ID: {}", transaction.getBookId());

	    boolean isValid = paymentService.validateBook(transaction.getBookId());
	    if (!isValid) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new PaymentResponse(null, 0f, transaction.getUserId(), "Failed: Book not available"));
	    }

	    PaymentResponse response = paymentService.processPayment(transaction);
	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
}
