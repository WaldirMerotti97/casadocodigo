package br.com.itau.casadocodigo.casadocodigoAPI.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.itau.casadocodigo.casadocodigoAPI.config.validacao.EstadoObrigatorioValidator;
import br.com.itau.casadocodigo.casadocodigoAPI.controller.form.NovaCompraForm;
import br.com.itau.casadocodigo.casadocodigoAPI.model.NovaCompra;
import br.com.itau.casadocodigo.casadocodigoAPI.model.NovaCompraDetalhesResponse;
import br.com.itau.casadocodigo.casadocodigoAPI.model.NovaCompraItensCarrinho;
import br.com.itau.casadocodigo.casadocodigoAPI.model.Estado;
import br.com.itau.casadocodigo.casadocodigoAPI.model.Pais;
import br.com.itau.casadocodigo.casadocodigoAPI.repository.NovaCompraRepository;
import br.com.itau.casadocodigo.casadocodigoAPI.repository.EstadoRepository;
import br.com.itau.casadocodigo.casadocodigoAPI.repository.NovaCompraDetalhesRepository;
import br.com.itau.casadocodigo.casadocodigoAPI.repository.PaisRepository;

@RestController
@RequestMapping(value = "casadocodigo/compras/")
public class NovaCompraController {

	@Autowired
	private NovaCompraRepository novaCompraRepository;
	@Autowired
	private PaisRepository paisRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private EstadoObrigatorioValidator estadoObrigatorioValidator;
	@Autowired
	private NovaCompraDetalhesRepository novaCompraDetalhesRepository;

	@InitBinder
	public void init(WebDataBinder binder) {
		binder.addValidators(estadoObrigatorioValidator);
	}

	@PostMapping(value = "inserirNovaCompra")
	@Transactional
	public ResponseEntity<String> inserirNovaCompra(@RequestBody(required = true) @Valid NovaCompraForm novaCompraForm,
			UriComponentsBuilder uriBuilder) {

		Optional<Pais> pais = paisRepository.findByNome(novaCompraForm.getPais());
		Optional<Estado> estado = estadoRepository.findByNome(novaCompraForm.getEstado());
		NovaCompra novaCompra = novaCompraForm.converter(pais, estado);
		novaCompraRepository.save(novaCompra);

		novaCompraForm.getCarrinhoComprasForm().getItens().forEach(item -> {

			novaCompraDetalhesRepository
					.save(new NovaCompraItensCarrinho(item.getIdLivro(), item.getQuantidade(), novaCompra));

		});

		URI uri = uriBuilder.path("casadocodigo/compras/detalhesCompras/{id}").buildAndExpand(novaCompra.getId())
				.toUri();
		return ResponseEntity.created(uri).body("O detalhes da compra estão disponíveis em: " + uri.toString());

	}

	@GetMapping(value = "detalhesCompras/{id}")
	@Transactional
	public ResponseEntity<NovaCompraDetalhesResponse> buscarDetalhesCompra(@PathVariable(required = true) int id) {

		Optional<NovaCompra> compra = novaCompraRepository.findById(id);
		Optional<List<NovaCompraItensCarrinho>> itensCarrinho = novaCompraDetalhesRepository.findByNovaCompra_Id(id);

		if (!compra.isPresent() || !itensCarrinho.isPresent())
			return ResponseEntity.badRequest().build();

		return ResponseEntity.ok(new NovaCompraDetalhesResponse(compra != null ? compra.get() : null,
				itensCarrinho != null ? itensCarrinho.get() : null));

	}

}
