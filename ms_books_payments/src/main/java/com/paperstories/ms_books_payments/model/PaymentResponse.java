package com.paperstories.ms_books_payments.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {
    private String tituloLibro;
    private Float totalPrice;
    private Long userId;
    private String status;
}