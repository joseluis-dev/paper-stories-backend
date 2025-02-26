package com.paperstories.ms_books_payments.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.paperstories.ms_books_payments.controller.model.PaymentRequest;
import com.paperstories.ms_books_payments.data.model.Payment;
import com.paperstories.ms_books_payments.service.PaymentsService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PaymentsController {
  
  private final PaymentsService service; //Inyeccion por constructor mediante @RequiredArgsConstructor. Y, también es inyección por interfaz.

    @PostMapping("/payments")
    public ResponseEntity<Payment> createPayment(@RequestBody @Valid PaymentRequest request) { //Se valida con Jakarta Validation API

        log.info("Creating payment...");
        try {
            Payment created = service.createPayment(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (HttpClientErrorException e) {
            log.error("Error creating payment", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalArgumentException e) {
            log.error("Error creating payment", e);
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Error creating payment", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/payments")
    public ResponseEntity<List<Payment>> getPayments() {

        List<Payment> payments = service.getAllPayments();
        if (payments != null) {
            return ResponseEntity.ok(payments);
        } else {
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    @GetMapping("/payments/{id}")
    public ResponseEntity<Payment> getPayment(@PathVariable String id) {

        Payment payment = service.getPayment(id);
        if (payment != null) {
            return ResponseEntity.ok(payment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
