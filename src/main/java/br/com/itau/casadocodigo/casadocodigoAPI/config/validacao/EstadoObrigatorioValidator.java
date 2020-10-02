package br.com.itau.casadocodigo.casadocodigoAPI.config.validacao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import br.com.itau.casadocodigo.casadocodigoAPI.controller.form.NovaCompraForm;
import br.com.itau.casadocodigo.casadocodigoAPI.model.Estado;
import br.com.itau.casadocodigo.casadocodigoAPI.repository.EstadoRepository;
import br.com.itau.casadocodigo.casadocodigoAPI.repository.PaisRepository;

@Component
public class EstadoObrigatorioValidator implements Validator {

	@Autowired
	private PaisRepository paisRepository;
	@PersistenceContext
	private EntityManager em;

	@Override
	public boolean supports(Class<?> clazz) {

		return NovaCompraForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		if (errors.hasErrors()) {
			return;
		}

		NovaCompraForm novaCompra = (NovaCompraForm) target;

		// pegar id do país
		int idPais = paisRepository.findByNome(novaCompra.getPais()).get().getId();
		System.out.println("Id do pais: " + idPais);

		// com base nele, ver se tem estados na tabela
		Query q = em.createNamedQuery("Estado.findByFKPais");
		q.setParameter(1, idPais);
		List<Estado> resultList = q.getResultList();

		System.out.println("Lista de estados para o pais " + novaCompra.getPais() + ":");
		resultList.forEach(estado -> System.out.print(estado.getNome() + " "));

		if (!resultList.isEmpty() && (novaCompra.getEstado().isEmpty() || novaCompra.getEstado() == null))
			errors.rejectValue("estado", null, "Informar o estado para este país é obrigatório!");

	}

}