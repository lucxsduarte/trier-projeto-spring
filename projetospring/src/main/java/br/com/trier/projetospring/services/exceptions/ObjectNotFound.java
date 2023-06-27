package br.com.trier.projetospring.services.exceptions;

public class ObjectNotFound extends RuntimeException{

	public ObjectNotFound(String message) {
		super(message);
	}
}
