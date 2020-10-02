package br.com.itau.casadocodigo.casadocodigoAPI.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.itau.casadocodigo.casadocodigoAPI.config.validacao.EstadoObrigatorioValidator;
import br.com.itau.casadocodigo.casadocodigoAPI.controller.form.CategoriaForm;
import br.com.itau.casadocodigo.casadocodigoAPI.controller.form.NovaCompraForm;
import br.com.itau.casadocodigo.casadocodigoAPI.model.Categoria;
import br.com.itau.casadocodigo.casadocodigoAPI.model.NovaCompra;
import br.com.itau.casadocodigo.casadocodigoAPI.model.Estado;
import br.com.itau.casadocodigo.casadocodigoAPI.model.Pais;
import br.com.itau.casadocodigo.casadocodigoAPI.repository.NovaCompraRepository;
import br.com.itau.casadocodigo.casadocodigoAPI.repository.EstadoRepository;
import br.com.itau.casadocodigo.casadocodigoAPI.repository.PaisRepository;

@RestController
@RequestMapping(value = "casadocodigo/novaCompra/")
public class NovaCompraController {

	@Autowired
	private NovaCompraRepository novaCompraRepository;
	@Autowired
	private PaisRepository paisRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private EstadoObrigatorioValidator estadoObrigatorioValidator;

	@InitBinder
	public void init(WebDataBinder binder) {
		binder.addValidators(estadoObrigatorioValidator);
	}

	@PostMapping(value = "inserirNovaCompra")
	@Transactional
	public ResponseEntity<NovaCompra> inserirCategoria(
			@RequestBody(required = true) @Valid NovaCompraForm novaCompraForm, UriComponentsBuilder uriBuilder) {

		Optional<Pais> pais = paisRepository.findByNome(novaCompraForm.getPais());
		Optional<Estado> estado = estadoRepository.findByNome(novaCompraForm.getEstado());
		NovaCompra novaCompra = novaCompraForm.converter(pais, estado);
		novaCompraRepository.save(novaCompra);

		URI uri = uriBuilder.path("/compras/{id}").buildAndExpand(novaCompra.getId()).toUri();
		return ResponseEntity.created(uri).body(novaCompra);

	}

}
