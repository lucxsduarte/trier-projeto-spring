package br.com.trier.projetospring.resources.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.trier.projetospring.services.exceptions.IntegrityViolation;
import br.com.trier.projetospring.services.exceptions.ObjectNotFound;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ObjectNotFound.class)
	public ResponseEntity<StandardError> objetoNaoEncontrado (ObjectNotFound object, HttpServletRequest request){
		StandardError erro = new StandardError(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), object.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
	}
	
	@ExceptionHandler(IntegrityViolation.class)
	public ResponseEntity<StandardError> violacaoIntegridade (IntegrityViolation violation, HttpServletRequest request) {
		StandardError erro = new StandardError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), violation.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
}
