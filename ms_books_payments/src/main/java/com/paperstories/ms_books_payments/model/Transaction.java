package com.paperstories.ms_books_payments.model;

import lombok.Data;

@Data
public class Transaction {
    private Long id;
    private Long bookId;
    private Long userId;
    private Integer quantity;
    private Float totalPrice;
    private String status;  // Success or Failure
}