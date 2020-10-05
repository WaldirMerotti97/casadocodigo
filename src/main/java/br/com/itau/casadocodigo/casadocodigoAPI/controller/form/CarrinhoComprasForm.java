package br.com.itau.casadocodigo.casadocodigoAPI.controller.form;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class CarrinhoComprasForm {

	@Min(value = 1, message = "É necessário inserir pelo menos 1 item no carrinho!")
	private BigDecimal total;
	@Valid
	@Size(min = 1, message = "É necessário inserir pelo menos 1 item no carrinho!")
	private List<ItemForm> itens;

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public List<ItemForm> getItens() {
		return itens;
	}

	public void setItens(List<ItemForm> itens) {
		this.itens = itens;
	}

}
