package br.com.itau.casadocodigo.casadocodigoAPI.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

//import br.com.itau.casadocodigo.casadocodigoAPI.config.validacao.EmailValidator;
import br.com.itau.casadocodigo.casadocodigoAPI.controller.form.AutorForm;
import br.com.itau.casadocodigo.casadocodigoAPI.controller.form.CategoriaForm;
import br.com.itau.casadocodigo.casadocodigoAPI.controller.form.LivroForm;
import br.com.itau.casadocodigo.casadocodigoAPI.model.Autor;
import br.com.itau.casadocodigo.casadocodigoAPI.model.Categoria;
import br.com.itau.casadocodigo.casadocodigoAPI.model.Livro;
import br.com.itau.casadocodigo.casadocodigoAPI.repository.AutorRepository;
import br.com.itau.casadocodigo.casadocodigoAPI.repository.CategoriaRepository;
import br.com.itau.casadocodigo.casadocodigoAPI.repository.LivroRepository;

@RestController
@RequestMapping(value = "casadocodigo/autor/")
public class AutorController {

	private AutorRepository autorRepository;

	public AutorController(AutorRepository autorRepository) {
		this.autorRepository = autorRepository;

	}

//	@InitBinder
//	public void init(WebDataBinder binder) {
//		binder.addValidators(emailValidator);
//	}

	@PostMapping(value = "inserirAutor")
	@Transactional
	public ResponseEntity<Autor> insereAutor(@RequestBody(required = true) @Valid AutorForm autorForm,
			UriComponentsBuilder uriBuilder) {

		Autor autor = autorForm.converter();
		autorRepository.save(autor);

		URI uri = uriBuilder.path("/autores/{id}").buildAndExpand(autor.getId()).toUri();
		return ResponseEntity.created(uri).body(autor);

	}

}