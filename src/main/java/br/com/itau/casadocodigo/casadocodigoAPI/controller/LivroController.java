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
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping(value = "casadocodigo/livro/")
public class LivroController {

	private AutorRepository autorRepository;
	private CategoriaRepository categoriaRepository;
	private LivroRepository livroRepository;

	public LivroController(AutorRepository autorRepository, CategoriaRepository categoriaRepository,
			LivroRepository livroRepository) {
		this.autorRepository = autorRepository;
		this.categoriaRepository = categoriaRepository;
		this.livroRepository = livroRepository;
	}

//	@InitBinder
//	public void init(WebDataBinder binder) {
//		binder.addValidators(emailValidator);
//	}

	@PostMapping(value = "inserirLivro")
	@Transactional
	public ResponseEntity<Livro> inserirLivro(@RequestBody(required = true) @Valid LivroForm livroForm,
			UriComponentsBuilder uriBuilder) {

		Optional<Autor> autor = autorRepository.findByNome(livroForm.getAutor());
		Optional<Categoria> categoria = categoriaRepository.findByNome(livroForm.getCategoria());
		Livro livro = livroForm.converter(autor, categoria);
		livroRepository.save(livro);

		URI uri = uriBuilder.path("/categoria/{id}").buildAndExpand(livro.getId()).toUri();
		return ResponseEntity.created(uri).body(livro);

	}

	@GetMapping(value = "/listarLivros")
	@Transactional
	public ResponseEntity<List<Livro>> listarLivros() {

		List<Livro> livros = livroRepository.findAll();

		return ResponseEntity.ok().body(livros);

	}

	@GetMapping(value = "/listarLivros/{id}")
	@Transactional
	public ResponseEntity<Optional<Livro>> listarLivrosPorId(@PathVariable(required = true) int id) {

		Optional<Livro> livro = livroRepository.findById(id);
		
		if(livro.isPresent()) {
			return ResponseEntity.ok().body(livro);
		}

		return ResponseEntity.notFound().build();

	}

}
