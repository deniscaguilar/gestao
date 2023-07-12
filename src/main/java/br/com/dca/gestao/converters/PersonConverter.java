package br.com.dca.gestao.converters;

import br.com.dca.gestao.controllers.request.PersonRequest;
import br.com.dca.gestao.controllers.response.PersonResponse;
import br.com.dca.gestao.entities.DocumentType;
import br.com.dca.gestao.entities.Person;

public class PersonConverter {

  public static PersonResponse toResponse(final Person person) {
    return PersonResponse.builder()
        .name(person.getName())
        .identifier(person.getDocument())
        .identifierType(person.getDocumentType())
        .build();
  }

  public static Person toPerson(final PersonRequest request, final DocumentType documentType) {
    return Person.builder()
        .name(request.getName())
        .document(request.getIdentifierWithoutFormat())
        .documentType(documentType)
        .build();
  }
}
