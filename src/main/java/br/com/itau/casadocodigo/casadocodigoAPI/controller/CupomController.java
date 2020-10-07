package br.com.itau.casadocodigo.casadocodigoAPI.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.itau.casadocodigo.casadocodigoAPI.controller.form.CupomForm;
import br.com.itau.casadocodigo.casadocodigoAPI.controller.form.LivroForm;
import br.com.itau.casadocodigo.casadocodigoAPI.model.Autor;
import br.com.itau.casadocodigo.casadocodigoAPI.model.Categoria;
import br.com.itau.casadocodigo.casadocodigoAPI.model.Cupom;
import br.com.itau.casadocodigo.casadocodigoAPI.model.Livro;
import br.com.itau.casadocodigo.casadocodigoAPI.repository.CupomRepository;

@RestController
@RequestMapping(value = "casadocodigo/cupons/")
public class CupomController {

	//1
	@Autowired
	private CupomRepository cupomRepository;

	@PostMapping(value = "criarCupom")
	@Transactional
	//1
	public ResponseEntity<Cupom> criarCupom(@RequestBody(required = true) @Valid CupomForm cupomForm,
			UriComponentsBuilder uriBuilder) {

		//1
		Cupom cupom = cupomForm.converter();
		cupomRepository.save(cupom);

		URI uri = uriBuilder.path("/cupom/{id}").buildAndExpand(cupom.getId()).toUri();
		return ResponseEntity.created(uri).body(cupom);

	}

	@PutMapping(value = "alterarCupom")
	@Transactional
	//1
	public ResponseEntity<Cupom> alterarCupom(@RequestBody(required = true) @Valid CupomForm cupomForm,
			UriComponentsBuilder uriBuilder) {

		//1
		Optional<Cupom> cupom = cupomRepository.findByCodigo(cupomForm.getCodigo());
		//1
		Cupom cupomAlterado = cupomForm.alterarCupom(cupom.get() != null ? cupom.get() : null);
		cupomRepository.save(cupomAlterado);

		return ResponseEntity.ok(cupom.get());

	}

}
