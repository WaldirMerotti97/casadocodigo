//package br.com.itau.casadocodigo.casadocodigoAPI.config.validacao;
//
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.validation.Errors;
//import org.springframework.validation.Validator;
//
//import br.com.itau.casadocodigo.casadocodigoAPI.controller.form.AutorForm;
//import br.com.itau.casadocodigo.casadocodigoAPI.model.Autor;
//import br.com.itau.casadocodigo.casadocodigoAPI.repository.AutorRepository;
//
//@Component
//public class EmailValidator implements Validator {
//
//	@Autowired
//	private AutorRepository autorRepository;
//
//	@Override
//	public boolean supports(Class<?> clazz) {
//
//		return AutorForm.class.isAssignableFrom(clazz);
//	}
//
//	@Override
//	public void validate(Object target, Errors errors) {
//
//		if (errors.hasErrors()) {
//			return;
//		}
//
//		AutorForm autorForm = (AutorForm) target;
//
//		Optional<Autor> possivelAutor = autorRepository.findByEmail(autorForm.getEmail());
//
//		if (possivelAutor.isPresent()) {
//			errors.rejectValue("email", null,
//					"Já existe um(a) outro(a) autor(a) com o mesmo email " + autorForm.getEmail());
//		}
//
//	}
//
//}
