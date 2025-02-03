package com.paperstories.ms_books_catalogue.controller.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookRequest {
  private String titulo;
  private String autor;
  private Date fechaPublicacion;
  private String categoria;
  private String isbn;
  private Float valoracion;
  private Boolean visible;
  private Float precio;
}
