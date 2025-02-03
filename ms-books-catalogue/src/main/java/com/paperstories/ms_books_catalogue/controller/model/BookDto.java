package com.paperstories.ms_books_catalogue.controller.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BookDto {
  private String titulo;
  private String autor;
  private Date fechaPublicacion;
  private String categoria;
  private String isbn;
  private Float valoracion;
  private Boolean visible;
  private Float precio;
}
