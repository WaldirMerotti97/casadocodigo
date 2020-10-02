package br.com.itau.casadocodigo.casadocodigoAPI.controller.form;

import java.util.Optional;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import br.com.itau.casadocodigo.casadocodigoAPI.model.NovaCompra;
import br.com.itau.casadocodigo.casadocodigoAPI.model.Estado;
import br.com.itau.casadocodigo.casadocodigoAPI.model.Pais;

public class NovaCompraForm {

	@Email(message = "O email inserido deve ser válido!")
	@NotBlank
	private String email;
	@NotBlank
	private String nome;
	@NotBlank
	private String sobrenome;
	@NotBlank
	private String documento;
	@NotBlank
	private String endereco;
	@NotBlank
	private String complemento;
	@NotBlank
	private String cidade;
	@NotBlank
	private String pais;
	private String estado;
	@NotBlank
	private String telefone;
	@NotBlank
	private String cep;

	public NovaCompraForm(@Email(message = "O email inserido deve ser válido!") @NotBlank String email,
			@NotBlank String nome, @NotBlank String sobrenome, @NotBlank String documento, @NotBlank String endereco,
			@NotBlank String complemento, @NotBlank String cidade, @NotBlank String pais, @NotBlank String estado,
			@NotBlank String telefone, @NotBlank String cep) {
		this.email = email;
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.documento = documento;
		this.endereco = endereco;
		this.complemento = complemento;
		this.cidade = cidade;
		this.pais = pais;
		this.estado = estado;
		this.telefone = telefone;
		this.cep = cep;
	}

	public NovaCompra converter(Optional<Pais> pais, Optional<Estado> estado) {

		return new NovaCompra(this.email, this.nome, this.sobrenome, this.documento, this.endereco, this.complemento,
				this.cidade, pais.isPresent() ? pais.get() : null, estado.isPresent() ? estado.get() : null,
				this.telefone, this.cep);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

}
