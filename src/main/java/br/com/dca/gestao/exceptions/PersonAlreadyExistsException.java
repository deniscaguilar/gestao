package br.com.dca.gestao.exceptions;

public class PersonAlreadyExistsException extends RuntimeException {

  public PersonAlreadyExistsException(final String message) {
    super(message);
  }
}
