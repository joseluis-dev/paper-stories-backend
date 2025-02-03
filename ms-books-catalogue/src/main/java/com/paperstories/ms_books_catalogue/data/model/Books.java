package com.paperstories.ms_books_catalogue.data.model;

import java.sql.Date;

import com.paperstories.ms_books_catalogue.data.utils.Consts;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
public class Books {
  @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = Consts.TITULO, unique = true)
	private String titulo;
	
	@Column(name = Consts.AUTOR)
	private String autor;
	
	@Column(name = Consts.FECHA_PUBLICACION)
	private Date fecha_publicacion;
	
	@Column(name = Consts.CATEGORIA)
	private String categoria;

  @Column(name = Consts.ISBN)
	private String isbn;

  @Column(name = Consts.VALORACION)
	private Float valoracion;

  @Column(name = Consts.VISIBILIDAD)
	private Boolean visibilidad;

  @Column(name = Consts.PRECIO)
	private Float precio;
}
