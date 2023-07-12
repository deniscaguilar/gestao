package br.com.dca.gestao.exceptions;

public class InvalidDocumentTypeException extends RuntimeException {

  public InvalidDocumentTypeException(final String message) {
    super(message);
  }
}
