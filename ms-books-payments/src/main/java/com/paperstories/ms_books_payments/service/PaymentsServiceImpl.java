package com.paperstories.ms_books_payments.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.paperstories.ms_books_payments.controller.model.PaymentRequest;
import com.paperstories.ms_books_payments.data.PaymentJpaRepository;
import com.paperstories.ms_books_payments.data.model.Payment;
import com.paperstories.ms_books_payments.data.model.PaymentDetail;
import com.paperstories.ms_books_payments.facade.BooksFacade;
import com.paperstories.ms_books_payments.facade.model.Book;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentsServiceImpl implements PaymentsService {
  private final PaymentJpaRepository repository;
  private final BooksFacade booksFacade;

  @Override
  public Payment createPayment(PaymentRequest request) {
    List<Book> books = request.getBooks().stream().map(booksFacade::getBook).filter(Objects::nonNull).toList();

    if(books.size() != request.getBooks().size() || books.stream().anyMatch(book -> !book.getVisible())) {
      return null;
    } else {
      // Crear detalles del pago
      List<PaymentDetail> details = books.stream()
      .map(book -> {
      // Validar que book no tenga valores nulos o incorrectos
      Long bookId = book.getId();
      Integer quantity = book.getCantidad();
      Float price = book.getPrecio();

      if (bookId == null || quantity == null || price == null || quantity <= 0 || price <= 0) {
          throw new IllegalArgumentException("Datos inválidos en el libro: " + book);
      }

      return PaymentDetail.builder()
              .bookId(bookId)
              .quantity(quantity)
              .price(price)
              .build();
  })
  .collect(Collectors.toList()); // Asegura que la lista sea mutable

  // Calcular el totalAmount sumando precio * cantidad
  BigDecimal totalAmount = details.stream()
          .map(detail -> detail.getPrice().multiply(Float.valueOf(detail.getQuantity())))
          .reduce(Float.ZERO, Float::add);

  // Construir el Payment
  Payment payment = Payment.builder()
          .totalAmount(totalAmount)
          .details(details)
          .build();

  // Establecer la relación en los detalles
  details.forEach(detail -> detail.setPayment(payment));
      repository.save(payment);
      return payment;
    }
  }

  @Override
  public Payment getPayment(String id) {
    return repository.findById(Long.valueOf(id)).orElse(null);
  }

  @Override
  public List<Payment> getAllPayments() {
    List<Payment> payments = repository.findAll();
    return payments.isEmpty() ? null : payments;
  }
}
