package br.com.dca.gestao.controllers;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import br.com.dca.gestao.controllers.request.PersonRequest;
import br.com.dca.gestao.controllers.response.ErrorResponse;
import br.com.dca.gestao.controllers.response.PersonResponse;
import br.com.dca.gestao.converters.PersonConverter;
import br.com.dca.gestao.entities.Person;
import br.com.dca.gestao.usecases.SavePerson;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/gestao/pessoas")
@Tag(name = "Gestão de Pessoas", description = "Endpoints responsáveis pela gestão de pessoas")
public class PersonManagementController {

  private SavePerson savePerson;

  @Operation(summary = "Cadastra um pessoa", description = "Deve cadastrar uma pessoa")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Pessoa Cadastrada"),
        @ApiResponse(
            responseCode = "422",
            description = "Tipo de documento inválido",
            content = {
              @Content(
                  mediaType = APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = ErrorResponse.class))
            })
      })
  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PersonResponse> save(@RequestBody @Valid PersonRequest request) {

    final Person person = savePerson.execute(request);

    return ResponseEntity.ok(PersonConverter.toResponse(person));
  }
}
