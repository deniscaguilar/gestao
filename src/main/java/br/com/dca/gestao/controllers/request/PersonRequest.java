package br.com.dca.gestao.controllers.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonRequest {

  @Schema(
      name = "name",
      description = "Deve ser preenchido com o nome da pessoa que será salva",
      required = true)
  @NotBlank
  private String name;

  @Schema(
      name = "identifier",
      description =
          "Deve ser preenchido com o número do documento da pessoa, podendo ser um CPF ou CNPJ",
      required = true)
  @NotBlank
  private String identifier;

  @JsonIgnore
  public String getIdentifierWithoutFormat() {
    return identifier.replaceAll("[^0-9]", "");
  }
}
