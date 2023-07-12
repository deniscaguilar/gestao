package br.com.dca.gestao.configurations;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

  @Value("${spring.application.name}")
  private String applicationName;

  @Bean
  public OpenApiCustomizer customOpenApiCustomizer() {
    Schema errorResponseSchema = new Schema();
    errorResponseSchema.setName("Error");
    errorResponseSchema.set$ref("#/components/schemas/ErrorResponse");

    return openApi ->
        openApi
            .getPaths()
            .values()
            .forEach(
                pathItem ->
                    pathItem
                        .readOperations()
                        .forEach(
                            operation -> {
                              ApiResponses apiResponses = operation.getResponses();
                              apiResponses.addApiResponse(
                                  "400", createApiResponse("BadRequest", errorResponseSchema));
                              apiResponses.addApiResponse(
                                  "401", createApiResponse("Unauthorized", errorResponseSchema));
                              apiResponses.addApiResponse(
                                  "403", createApiResponse("Forbidden", errorResponseSchema));
                              apiResponses.addApiResponse(
                                  "404",
                                  createApiResponse("Resource Not Found", errorResponseSchema));
                              apiResponses.addApiResponse(
                                  "500", createApiResponse("Server Error", errorResponseSchema));
                            }));
  }

  private ApiResponse createApiResponse(String message, Schema schema) {
    MediaType mediaType = new MediaType();
    mediaType.schema(schema);
    return new ApiResponse()
        .description(message)
        .content(
            new Content()
                .addMediaType(
                    org.springframework.http.MediaType.APPLICATION_JSON_VALUE, mediaType));
  }

  @Bean
  public OpenAPI customOpenAPI() {

    return new OpenAPI()
        .info(
            new Info()
                .title(applicationName)
                .version("1.0")
                .description("Microserviço para gestão de pessoas"));
  }
}
