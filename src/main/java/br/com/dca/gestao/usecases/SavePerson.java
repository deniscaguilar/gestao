package br.com.dca.gestao.usecases;

import br.com.dca.gestao.controllers.request.PersonRequest;
import br.com.dca.gestao.converters.PersonConverter;
import br.com.dca.gestao.entities.DocumentType;
import br.com.dca.gestao.entities.Person;
import br.com.dca.gestao.exceptions.PersonAlreadyExistsException;
import br.com.dca.gestao.gateways.PersonGateway;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class SavePerson {

  private PersonGateway personGateway;

  public Person execute(final PersonRequest request) {
    log.info("Save person: {}", request);

    validatePersonAlreadyExists(request.getIdentifierWithoutFormat());

    final DocumentType documentType =
        DocumentType.getDocumentType(request.getIdentifierWithoutFormat());
    final Person person = PersonConverter.toPerson(request, documentType);
    return personGateway.save(person);
  }

  private void validatePersonAlreadyExists(final String document) {
    final Optional<Person> optionalPerson = personGateway.findByDocument(document);
    if (optionalPerson.isPresent()) {
      throw new PersonAlreadyExistsException(
          String.format("Já existe uma pessoa cadastrada com o identificador: %s", document));
    }
  }
}
