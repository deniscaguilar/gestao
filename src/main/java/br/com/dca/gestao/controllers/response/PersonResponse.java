package br.com.dca.gestao.controllers.response;

import br.com.dca.gestao.entities.DocumentType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonResponse {

  private String name;

  private DocumentType identifierType;

  private String identifier;
}
