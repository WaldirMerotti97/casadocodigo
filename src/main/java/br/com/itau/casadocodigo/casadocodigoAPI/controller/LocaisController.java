package br.com.itau.casadocodigo.casadocodigoAPI.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.itau.casadocodigo.casadocodigoAPI.config.validacao.EstadoObrigatorioValidator;
import br.com.itau.casadocodigo.casadocodigoAPI.controller.form.AutorForm;
import br.com.itau.casadocodigo.casadocodigoAPI.controller.form.EstadoForm;
import br.com.itau.casadocodigo.casadocodigoAPI.controller.form.PaisForm;
import br.com.itau.casadocodigo.casadocodigoAPI.model.Autor;
import br.com.itau.casadocodigo.casadocodigoAPI.model.Estado;
import br.com.itau.casadocodigo.casadocodigoAPI.model.Pais;
import br.com.itau.casadocodigo.casadocodigoAPI.repository.PaisRepository;
import br.com.itau.casadocodigo.casadocodigoAPI.repository.EstadoRepository;

@RestController
@RequestMapping(value = "casadocodigo/locais/")
public class LocaisController {

	@Autowired
	private PaisRepository paisRepository;
	@Autowired
	private EstadoRepository estadoRepository;

	@PostMapping(value = "criarPais")
	@Transactional
	public ResponseEntity<Pais> criarPais(@RequestBody(required = true) @Valid PaisForm paisForm,
			UriComponentsBuilder uriBuilder) {

		Pais pais = paisForm.converter();
		paisRepository.save(pais);

		URI uri = uriBuilder.path("/paises/{id}").buildAndExpand(pais.getId()).toUri();
		return ResponseEntity.created(uri).body(pais);

	}

	@PostMapping(value = "criarEstado")
	@Transactional
	public ResponseEntity<Estado> criarEstado(@RequestBody(required = true) @Valid EstadoForm estadoForm,
			UriComponentsBuilder uriBuilder) {

		Optional<Pais> pais = paisRepository.findByNome(estadoForm.getPais());
		Estado estado = estadoForm.converter(pais.isPresent() ? pais.get() : null);
		estadoRepository.save(estado);

		URI uri = uriBuilder.path("/estados/{id}").buildAndExpand(estado.getId()).toUri();
		return ResponseEntity.created(uri).body(estado);

	}

}
