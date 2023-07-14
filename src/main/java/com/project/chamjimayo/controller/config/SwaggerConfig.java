package com.project.chamjimayo.controller.config;

import com.project.chamjimayo.controller.dto.ErrorCode;
import com.project.chamjimayo.controller.dto.ErrorResponse;
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
			Schema<ErrorCode> codeSchema = new Schema<>();
			codeSchema.type("enum");
			codeSchema._enum(List.of(ErrorCode.values()));

			Schema<ErrorResponse> errorResponseSchema = new Schema<>();
			errorResponseSchema.type("object");
			errorResponseSchema.addProperty("code", codeSchema);
			errorResponseSchema.addProperty("msg", new Schema<>().type("string"));
			errorResponseSchema.example(ErrorResponse.create(ErrorCode.AUTHENTICATION_EXCEPTION,
				"인증 오류입니다."));

			for (PathItem p : openApi.getPaths().values()) {
				for (Operation o : p.readOperations()) {
					ApiResponses responses = o.getResponses();
					responses.addApiResponse("401",
						createApiResponse("Unauthorized", errorResponseSchema));
				}
			}
		};
	}

	private ApiResponse createApiResponse(String message, Schema<ErrorResponse> schema) {
		MediaType mediaType = new MediaType();
		mediaType.schema(schema);
		return new ApiResponse().description(message)
			.content(
				new Content().addMediaType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE,
					mediaType));
	}
}