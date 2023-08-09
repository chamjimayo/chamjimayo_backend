package com.project.chamjimayo.controller.config;

import com.project.chamjimayo.controller.dto.response.ApiStandardResponse;
import com.project.chamjimayo.controller.dto.response.ErrorResponse;
import com.project.chamjimayo.service.exception.ErrorStatus;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import java.util.List;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(servers = {
    @Server(url = "/", description = "Default Server URL")
})
public class SwaggerConfig {

  @Bean
  public GroupedOpenApi publicApi() {
    return GroupedOpenApi.builder()
        .group("v1-definition")
        .pathsToMatch("/**")
        .build();
  }

  @Bean
  public OpenAPI springShopOpenAPI() {
    SecurityScheme securityScheme = new SecurityScheme()
        .type(Type.APIKEY)
        .description("Api 키")
        .in(In.HEADER)
        .name("x-api-key");

    SecurityRequirement securityRequirement = new SecurityRequirement()
        .addList("api key");

    return new OpenAPI()
        .components(new Components().addSecuritySchemes("api key", securityScheme))
        .addSecurityItem(securityRequirement)
        .info(new Info().title("Chamjimayo project")
            .contact(new Contact().name("chamjimayo").email("dontholditin0@gmail.com"))
            .description("참지마요 프로젝트 API 명세서")
            .summary("참지마요 백엔드 API입니다.")
            .version("v0.0.1"));
  }

  @Bean
  public GlobalOpenApiCustomizer openApiCustomizer() {
    return openApi -> {
      for (PathItem p : openApi.getPaths().values()) {
        for (Operation o : p.readOperations()) {
          ApiResponses responses = o.getResponses();
          responses.addApiResponse("401",
              createApiResponse("Api 키 오류",
                  createErrorResponseSchema(ErrorStatus.AUTHENTICATION_EXCEPTION)));
          responses.addApiResponse("405",
              createApiResponse("Method Not Allowed",
                  createErrorResponseSchema(ErrorStatus.METHOD_NOT_ALLOWED_EXCEPTION)));
          responses.addApiResponse("500",
              createApiResponse("Internal Server Error",
                  createErrorResponseSchema(ErrorStatus.INTERNAL_SERVER_ERROR)));
        }
      }
    };
  }

  private Schema<ApiStandardResponse<ErrorResponse>> createErrorResponseSchema(
      ErrorStatus errorStatus) {
    Schema<ErrorStatus> codeSchema = new Schema<>();
    codeSchema.type("enum");
    codeSchema._enum(List.of(errorStatus));

    Schema<ErrorResponse> errorResponseSchema = new Schema<>();
    errorResponseSchema.type("object");
    errorResponseSchema.addProperty("code", codeSchema);
    errorResponseSchema.addProperty("msg", new Schema<>().type("string"));

    Schema<ApiStandardResponse<ErrorResponse>> apiStandardResponseSchema = new Schema<>();
    apiStandardResponseSchema.type("object");
    apiStandardResponseSchema.addProperty("code", new Schema<>().type("int"));
    apiStandardResponseSchema.addProperty("msg", new Schema<>().type("string"));
    apiStandardResponseSchema.addProperty("data", errorResponseSchema);

    apiStandardResponseSchema.example(
        ApiStandardResponse.fail(
            ErrorResponse.create(errorStatus, getErrorMessage(errorStatus))));
    return apiStandardResponseSchema;
  }

  private String getErrorMessage(ErrorStatus errorStatus) {
    switch (errorStatus) {
      case AUTHENTICATION_EXCEPTION:
        return "Api 키가 올바르지 않습니다.";
      case METHOD_NOT_ALLOWED_EXCEPTION:
        return "지원하지 않는 HTTP Method입니다.";
      case INTERNAL_SERVER_ERROR:
        return "서버 내부 오류가 발생했습니다.";
      default:
        return "";
    }
  }

  private ApiResponse createApiResponse(String message,
      Schema<ApiStandardResponse<ErrorResponse>> schema) {
    MediaType mediaType = new MediaType();
    mediaType.schema(schema);
    return new ApiResponse().description(message)
        .content(
            new Content().addMediaType(
                org.springframework.http.MediaType.APPLICATION_JSON_VALUE,
                mediaType));
  }
}