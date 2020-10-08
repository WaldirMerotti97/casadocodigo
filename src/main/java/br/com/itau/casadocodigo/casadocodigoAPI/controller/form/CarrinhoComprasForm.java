package br.com.itau.casadocodigo.casadocodigoAPI.controller.form;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import br.com.itau.casadocodigo.casadocodigoAPI.config.validacao.anotacoes.CupomUtilizavel;
import br.com.itau.casadocodigo.casadocodigoAPI.model.Cupom;
import br.com.itau.casadocodigo.casadocodigoAPI.model.Livro;
import br.com.itau.casadocodigo.casadocodigoAPI.repository.CupomRepository;
import br.com.itau.casadocodigo.casadocodigoAPI.repository.LivroRepository;

public class CarrinhoComprasForm {

	@Min(value = 1, message = "É necessário inserir pelo menos 1 item no carrinho!")
	private BigDecimal total;
	@CupomUtilizavel
	private String cupom;
	// 1
	@Valid
	@Size(min = 1, message = "É necessário inserir pelo menos 1 item no carrinho!")
	private List<ItemForm> itens;

	public Map<String, BigDecimal> verificaDesconto(LivroRepository livroRepository, CupomRepository cupomRepository) {

		BigDecimal valorTotalSemDesconto = new BigDecimal(0);
		BigDecimal valorTotalComDesconto = new BigDecimal(0);

		Map<String, BigDecimal> valorAPagar = new HashMap<>();

		for (ItemForm item : this.itens) {
			Optional<Livro> livro = livroRepository.findById(item.getIdLivro());
			valorTotalSemDesconto = valorTotalSemDesconto
					.add(livro.get().getPreco().multiply(new BigDecimal(item.getQuantidade())));
		}

		if (this.cupom.length() > 0) {

			Cupom cupom = cupomRepository.findByCodigo(this.cupom).get();

			valorTotalComDesconto = valorTotalComDesconto.add(valorTotalSemDesconto
					.multiply(new BigDecimal(cupom.getPercentualDesconto() - 1).multiply(new BigDecimal(-1))));

			cupom.setDataUtilizado(LocalDate.now());

			cupomRepository.save(cupom);

			valorAPagar.put("ValorComDesconto", valorTotalComDesconto);

		}

		valorAPagar.put("ValorSemDesconto", valorTotalSemDesconto);

		return valorAPagar;

	}

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

	public String getCupom() {
		return cupom;
	}

	public void setCupom(String cupom) {
		this.cupom = cupom;
	}

}
