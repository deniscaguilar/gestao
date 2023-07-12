package br.com.dca.gestao.controllers;

import br.com.dca.gestao.controllers.response.ErrorResponse;
import br.com.dca.gestao.exceptions.InvalidDocumentTypeException;
import br.com.dca.gestao.exceptions.PersonAlreadyExistsException;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
@AllArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {
    log.error(
        "Erro durante a validação dos campos obrigatórios da request. StatusCode: {}",
        status.value());

    Map<String, String> fieldErros = new HashMap<>();
    for (FieldError objectError : ex.getBindingResult().getFieldErrors()) {
      fieldErros.put(objectError.getField(), objectError.getDefaultMessage());
    }

    return new ResponseEntity<>(
        ErrorResponse.builder()
            .message("Existem campos obrigatórios não informados")
            .fieldErrors(fieldErros)
            .build(),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(InvalidDocumentTypeException.class)
  public @ResponseBody ResponseEntity<ErrorResponse> handlerInvalidDocumentTypeException(
      InvalidDocumentTypeException ex) {
    log.error(
        "Erro na gestão de pessoas. StatusCode: {} e Mensagem de Erro {} ",
        HttpStatus.BAD_REQUEST.value(),
        ex.getMessage());

    return new ResponseEntity<>(
        ErrorResponse.builder().message(ex.getMessage()).build(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(PersonAlreadyExistsException.class)
  public @ResponseBody ResponseEntity<ErrorResponse> handlerPersonAlreadyExistsException(
      PersonAlreadyExistsException ex) {
    log.error(
        "Erro na gestão de pessoas. StatusCode: {} e Mensagem de Erro {} ",
        HttpStatus.BAD_REQUEST.value(),
        ex.getMessage());

    return new ResponseEntity<>(
        ErrorResponse.builder().message(ex.getMessage()).build(), HttpStatus.UNPROCESSABLE_ENTITY);
  }
}
