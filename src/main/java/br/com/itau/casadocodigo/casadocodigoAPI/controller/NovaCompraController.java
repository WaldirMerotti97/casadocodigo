package br.com.itau.casadocodigo.casadocodigoAPI.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Map;
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
import br.com.itau.casadocodigo.casadocodigoAPI.repository.CupomRepository;
import br.com.itau.casadocodigo.casadocodigoAPI.repository.EstadoRepository;
import br.com.itau.casadocodigo.casadocodigoAPI.repository.LivroRepository;
import br.com.itau.casadocodigo.casadocodigoAPI.repository.NovaCompraDetalhesRepository;
import br.com.itau.casadocodigo.casadocodigoAPI.repository.PaisRepository;

@RestController
@RequestMapping(value = "casadocodigo/compras/")
public class NovaCompraController {

	// 1
	@Autowired
	private NovaCompraRepository novaCompraRepository;
	// 1
	@Autowired
	private PaisRepository paisRepository;
	// 1
	@Autowired
	private EstadoRepository estadoRepository;
	// 1
	@Autowired
	private EstadoObrigatorioValidator estadoObrigatorioValidator;
	// 1
	@Autowired
	private NovaCompraDetalhesRepository novaCompraDetalhesRepository;
	// 1
	@Autowired
	private LivroRepository livroRepository;
	// 1
	@Autowired
	private CupomRepository cupomRepository;

	@InitBinder
	public void init(WebDataBinder binder) {
		binder.addValidators(estadoObrigatorioValidator);
	}

	@PostMapping(value = "inserirNovaCompra")
	@Transactional
	// 1
	public ResponseEntity<String> inserirNovaCompra(@RequestBody(required = true) @Valid NovaCompraForm novaCompraForm,
			UriComponentsBuilder uriBuilder) {

		// 1
		Optional<Pais> pais = paisRepository.findByNome(novaCompraForm.getPais());
		// 1
		Optional<Estado> estado = estadoRepository.findByNome(novaCompraForm.getEstado());
		// 1
		NovaCompra novaCompra = novaCompraForm.converter(pais, estado);

		Map<String, BigDecimal> valorAPagar = novaCompraForm.getCarrinhoComprasForm().verificaDesconto(livroRepository,
				cupomRepository);

		novaCompra.setValorSemDesconto(valorAPagar.get("ValorSemDesconto"));
		novaCompra.setValorComDesconto(valorAPagar.get("ValorComDesconto"));

		novaCompraRepository.save(novaCompra);

		// 1
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

		// 1
		Optional<NovaCompra> compra = novaCompraRepository.findById(id);
		// 1
		Optional<List<NovaCompraItensCarrinho>> itensCarrinho = novaCompraDetalhesRepository.findByNovaCompra_Id(id);

		// 1
		if (!compra.isPresent() || !itensCarrinho.isPresent())
			return ResponseEntity.badRequest().build();

		return ResponseEntity.ok(new NovaCompraDetalhesResponse(compra != null ? compra.get() : null,
				itensCarrinho != null ? itensCarrinho.get() : null, livroRepository));

	}

}
