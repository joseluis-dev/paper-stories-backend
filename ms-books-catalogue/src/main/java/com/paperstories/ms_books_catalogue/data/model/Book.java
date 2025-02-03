package com.paperstories.ms_books_catalogue.data.model;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.paperstories.ms_books_catalogue.controller.model.BookDto;
import com.paperstories.ms_books_catalogue.data.utils.Consts;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "libros")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Book {
  @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = Consts.TITULO, unique = true)
	private String titulo;
	
	@Column(name = Consts.AUTOR)
	private String autor;
	
	@Column(name = Consts.FECHAPUBLICACION)
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date fechaPublicacion;
	
	@Column(name = Consts.CATEGORIA)
	private String categoria;

  @Column(name = Consts.ISBN)
	private String isbn;

  @Column(name = Consts.VALORACION)
	private Float valoracion;

  @Column(name = Consts.VISIBLE)
	private Boolean visible;

  @Column(name = Consts.PRECIO)
	private Float precio;

	public void update(BookDto updateRequest) {
		this.titulo = updateRequest.getTitulo();
		this.autor = updateRequest.getAutor();
		this.fechaPublicacion = updateRequest.getFechaPublicacion();
		this.categoria = updateRequest.getCategoria();
		this.isbn = updateRequest.getIsbn();
		this.valoracion = updateRequest.getValoracion();
		this.visible = updateRequest.getVisible();
		this.precio = updateRequest.getPrecio();
	}
}
