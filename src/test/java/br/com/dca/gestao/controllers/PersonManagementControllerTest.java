package br.com.dca.gestao.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.dca.gestao.controllers.request.PersonRequest;
import br.com.dca.gestao.controllers.response.PersonResponse;
import br.com.dca.gestao.entities.DocumentType;
import br.com.dca.gestao.entities.Person;
import br.com.dca.gestao.exceptions.InvalidDocumentTypeException;
import br.com.dca.gestao.exceptions.PersonAlreadyExistsException;
import br.com.dca.gestao.usecases.SavePerson;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(PersonManagementController.class)
public class PersonManagementControllerTest {

  @MockBean private SavePerson savePerson;

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper mapper;

  @Test
  public void test_save_person_success() throws Exception {
    when(savePerson.execute(any())).thenReturn(mockPerson());

    MvcResult mvcResult =
        mockMvc
            .perform(
                post("/api/v1/gestao/pessoas")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .content(mapper.writeValueAsString(mockRequest())))
            .andExpect(status().isOk())
            .andReturn();

    String content = mvcResult.getResponse().getContentAsString();
    PersonResponse personResponse = mapper.readValue(content, PersonResponse.class);

    Assertions.assertNotNull(personResponse);
    Assertions.assertEquals("Denis", personResponse.getName());
    Assertions.assertEquals("25487466887", personResponse.getIdentifier());
    Assertions.assertEquals(DocumentType.CPF, personResponse.getIdentifierType());
  }

  @Test
  public void test_save_person_invalid_document_person() throws Exception {
    when(savePerson.execute(any())).thenThrow(InvalidDocumentTypeException.class);

    mockMvc
        .perform(
            post("/api/v1/gestao/pessoas")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(mockRequest())))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void test_save_person_already_exists() throws Exception {
    when(savePerson.execute(any())).thenThrow(PersonAlreadyExistsException.class);

    mockMvc
        .perform(
            post("/api/v1/gestao/pessoas")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(mockRequest())))
        .andExpect(status().isUnprocessableEntity());
  }

  private Person mockPerson() {
    return Person.builder()
        .id(1l)
        .name("Denis")
        .document("25487466887")
        .documentType(DocumentType.CPF)
        .build();
  }

  private PersonRequest mockRequest() {
    return PersonRequest.builder().name("Dênis").identifier("25487466887").build();
  }
}
