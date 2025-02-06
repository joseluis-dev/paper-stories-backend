package com.paperstories.ms_books_payments.data;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paperstories.ms_books_payments.data.model.Payment;

public interface PaymentJpaRepository extends JpaRepository<Payment, Long> {
  
}
