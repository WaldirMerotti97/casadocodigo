package br.com.itau.casadocodigo.casadocodigoAPI.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Livro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotBlank
	private String titulo;
	@NotBlank @Size(max = 500)
	private String resumoLivro;
	private String sumario;
	@DecimalMin(value = "20.0")
	private BigDecimal preco;
	@Min(value = 100)
	private int nroPaginas;
	@NotBlank
	private String identificadorISBN;
	@Future
	private LocalDate dataPublicacao;
	
	

}
