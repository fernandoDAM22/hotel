package com.hotel.hotel.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "Hotel",
        version = "1.0",
        description = "Esta api permite la gestion de un hotel, permitiendo gestionar los usuarios, las habitaciones o las reservas",
        contact = @Contact(
                name = "Fernando Gil González",
                email = "fernandoesmr@gmail.com",
                url = "https://fernandodam22.github.io/"
        )
))
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("API")
                .pathsToMatch("/api/**")
                .build();
    }
}
