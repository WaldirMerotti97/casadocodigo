package br.com.itau.casadocodigo.casadocodigoAPI.config.validacao;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.itau.casadocodigo.casadocodigoAPI.config.validacao.anotacoes.QuantidadeLivros;
import br.com.itau.casadocodigo.casadocodigoAPI.controller.form.CarrinhoComprasForm;

public class QuantidadeLivrosValidator implements ConstraintValidator<QuantidadeLivros, Object> {

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {

		//1
		CarrinhoComprasForm carrinhoComprasForm = (CarrinhoComprasForm) value;

		return carrinhoComprasForm.getTotal().intValue() == carrinhoComprasForm.getItens().size();
	}

}
