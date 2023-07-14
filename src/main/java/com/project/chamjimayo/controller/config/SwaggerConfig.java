package com.project.chamjimayo.controller.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public GroupedOpenApi publicApi() {
    return GroupedOpenApi.builder()
        .group("v1-definition")
        .pathsToMatch("/api/**")
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
        .addList("securityScheme");

    return new OpenAPI()
        .components(new Components().addSecuritySchemes("api key", securityScheme))
        .addSecurityItem(securityRequirement)
        .info(new Info().title("Chamjimayo project")
            .contact(new Contact().name("chamjimayo").email("dontholditin0@gmail.com"))
            .description("참지마요 프로젝트 API 명세서")
            .summary("참지마요 백엔드 API입니다.")
            .version("v0.0.1"));
  }
}
