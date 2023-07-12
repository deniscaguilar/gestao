package br.com.dca.gestao.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import br.com.dca.gestao.controllers.request.PersonRequest;
import br.com.dca.gestao.entities.DocumentType;
import br.com.dca.gestao.entities.Person;
import br.com.dca.gestao.exceptions.InvalidDocumentTypeException;
import br.com.dca.gestao.exceptions.PersonAlreadyExistsException;
import br.com.dca.gestao.gateways.PersonGateway;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class SavePersonTest {

  @Mock PersonGateway personGateway;

  @InjectMocks SavePerson savePerson;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  void deveRetornarExceptionTamanhoDocumentoInvalido() {
    when(personGateway.findByDocument(Mockito.anyString())).thenReturn(Optional.empty());

    InvalidDocumentTypeException thrown =
        assertThrows(
            InvalidDocumentTypeException.class,
            () -> savePerson.execute(mockIdentifierInvalidRequest()));

    assertThat(thrown.getMessage()).isEqualTo("Tamanho do documento inválido.");
  }

  @Test
  void deveRetornarExceptionJáExistePessoaCadastrada() {
    when(personGateway.findByDocument(anyString())).thenReturn(Optional.of(mockPerson()));

    PersonAlreadyExistsException thrown =
        assertThrows(PersonAlreadyExistsException.class, () -> savePerson.execute(mockRequest()));

    assertThat(thrown.getMessage())
        .isEqualTo("Já existe uma pessoa cadastrada com o identificador: 25487466887");
  }

  @Test
  void deveSalvarPessoaComSucesso() {
    when(personGateway.findByDocument(anyString())).thenReturn(Optional.empty());
    when(personGateway.save(any())).thenReturn(mockPerson());

    Person person = savePerson.execute(mockRequest());

    assertThat(person).isNotNull();
    assertThat(person.getId()).isEqualTo(1l);
    assertThat(person.getName()).isEqualTo("Denis");
    assertThat(person.getDocument()).isEqualTo("25487466887");
    assertThat(person.getDocumentType()).isEqualTo(DocumentType.CPF);
  }

  private PersonRequest mockRequest() {
    return PersonRequest.builder().name("Dênis").identifier("25487466887").build();
  }

  private PersonRequest mockIdentifierInvalidRequest() {
    return PersonRequest.builder().name("Dênis").identifier("254874668").build();
  }

  private Person mockPerson() {
    return Person.builder()
        .id(1l)
        .name("Denis")
        .document("25487466887")
        .documentType(DocumentType.CPF)
        .build();
  }

  private Person mockPersonWithoutId() {
    return Person.builder()
        .name("Denis")
        .document("25487466887")
        .documentType(DocumentType.CPF)
        .build();
  }
}
