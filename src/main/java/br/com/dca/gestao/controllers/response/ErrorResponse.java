package br.com.dca.gestao.controllers.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

  @Schema(
      name = "message",
      description = "Contém a mensagem de erro da operação",
      example = "Mensagem de Erro")
  private String message;

  @Schema(
      name = "fieldErrors",
      description = "Mapa contendo cada campo dos objetos da request que contém erros")
  private Map<String, String> fieldErrors = new HashMap<>();
}
