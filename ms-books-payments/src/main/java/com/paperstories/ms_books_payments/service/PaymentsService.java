package com.paperstories.ms_books_payments.service;

import java.util.List;

import com.paperstories.ms_books_payments.controller.model.PaymentRequest;
import com.paperstories.ms_books_payments.data.model.Payment;

public interface PaymentsService {

  Payment createPayment(PaymentRequest request);
  Payment getPayment(String id);
  List<Payment> getAllPayments();

}
