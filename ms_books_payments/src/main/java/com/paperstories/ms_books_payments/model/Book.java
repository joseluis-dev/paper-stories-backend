package com.paperstories.ms_books_payments.model;

import java.sql.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class Book {
    private Long id;
    private String titulo;
    private String autor;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date fechaPublicacion;

    private String categoria;
    private String isbn;
    private Float valoracion;
    private Boolean visible;
    private Float precio;
}
