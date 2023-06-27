package br.com.trier.projetospring.services.exceptions;

public class IntegrityViolation extends RuntimeException{

	public IntegrityViolation(String message) {
		super(message);
	}
}
