package br.com.itau.casadocodigo.casadocodigoAPI.controller;

import java.net.URI;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import br.com.itau.casadocodigo.casadocodigoAPI.controller.form.AutorForm;
import br.com.itau.casadocodigo.casadocodigoAPI.controller.form.CategoriaForm;
import br.com.itau.casadocodigo.casadocodigoAPI.model.Autor;
import br.com.itau.casadocodigo.casadocodigoAPI.model.Categoria;
import br.com.itau.casadocodigo.casadocodigoAPI.repository.AutorRepository;
import br.com.itau.casadocodigo.casadocodigoAPI.repository.CategoriaRepository;

@RestController
@RequestMapping(value = "casadocodigo")
public class ControladorGenerico {

	private AutorRepository autorRepository;
	private CategoriaRepository categoriaRepository;

	public ControladorGenerico(AutorRepository autorRepository, CategoriaRepository categoriaRepository) {
		this.autorRepository = autorRepository;
		this.categoriaRepository = categoriaRepository;
	}

	@PostMapping(value = "/inserirAutor")
	@Transactional
	public ResponseEntity<Autor> insereAutor(@RequestBody(required = true) @Valid AutorForm autorForm,
			UriComponentsBuilder uriBuilder) {

		Autor autor = autorForm.converter();
		autorRepository.save(autor);

		URI uri = uriBuilder.path("/autores/{id}").buildAndExpand(autor.getId()).toUri();
		return ResponseEntity.created(uri).body(autor);

	}

	@PostMapping(value = "/inserirCategoria")
	@Transactional
	public ResponseEntity<Categoria> inserirCategoria(@RequestBody(required = true) @Valid CategoriaForm categoriaForm,
			UriComponentsBuilder uriBuilder) {

		Categoria categoria = categoriaForm.converter();
		categoriaRepository.save(categoria);

		URI uri = uriBuilder.path("/categoria/{id}").buildAndExpand(categoria.getId()).toUri();
		return ResponseEntity.created(uri).body(categoria);

	}

}
