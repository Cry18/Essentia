package com.essentia.essentiaadministration.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Essentia — Administration API")
                        .description("""
                                Administration endpoints for the fragrance catalog.

                                **Access:** all endpoints require a JWT with `ROLE_ADMIN`.
                                Click **Authorize** and paste the token obtained from `POST /api/auth/login`
                                on the essentiauser module (:8081).
                                """)
                        .version("1.0.0"))
                // Adds the "Authorize" button in Swagger UI to enter the Bearer token
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .name("bearerAuth")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
}
