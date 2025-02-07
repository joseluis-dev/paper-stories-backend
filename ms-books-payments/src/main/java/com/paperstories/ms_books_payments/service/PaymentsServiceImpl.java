package com.paperstories.ms_books_payments.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.paperstories.ms_books_payments.controller.model.PaymentDto;
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
    List<PaymentDto> paymentDtos = request.getPayments();
    List<String> bookIds = paymentDtos.stream().map(PaymentDto::getBookId).collect(Collectors.toList());
    List<Book> books = bookIds.stream().map(booksFacade::getBook).filter(Objects::nonNull).toList();

    if (books.size() != bookIds.size() || books.stream().anyMatch(book -> !book.getVisible())) {
      return null;
    } else {
      // Crear detalles del pago
      List<PaymentDetail> details = paymentDtos.stream().map(paymentDto -> {
        Book book = books.stream().filter(b -> b.getId().toString().equals(paymentDto.getBookId())).findFirst().orElse(null);
        if (book == null) {
          throw new IllegalArgumentException("Libro no encontrado: " + paymentDto.getBookId());
        }

        Long bookId = book.getId();
        Float price = book.getPrecio();
        Integer quantity = paymentDto.getQuantity();

        if (bookId == null || quantity == null || price == null || price <= 0) {
          throw new IllegalArgumentException("Datos inválidos en el libro: " + book);
        }

        return PaymentDetail.builder()
                .bookId(bookId)
                .quantity(quantity)
                .price(price)
                .build();
      }).collect(Collectors.toList());

      Float totalAmount = details.stream()
              .map(detail -> detail.getPrice() * detail.getQuantity())
              .reduce(0f, Float::sum);

      Payment payment = Payment.builder()
              .totalAmount(totalAmount)
              .details(details)
              .build();

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
