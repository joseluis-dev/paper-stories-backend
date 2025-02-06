package com.paperstories.ms_books_payments.controller.model;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
  
  @NotNull(message = "`books` cannot be null")
	@NotEmpty(message = "`books` cannot be empty")
	private List<String> books;

}
