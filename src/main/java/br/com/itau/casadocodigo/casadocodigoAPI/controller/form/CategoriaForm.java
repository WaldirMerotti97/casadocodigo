package br.com.itau.casadocodigo.casadocodigoAPI.controller.form;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import br.com.itau.casadocodigo.casadocodigoAPI.model.Categoria;

public class CategoriaForm {

	@NotBlank
	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Categoria converter() {

		return new Categoria(this.nome);
	}

}
