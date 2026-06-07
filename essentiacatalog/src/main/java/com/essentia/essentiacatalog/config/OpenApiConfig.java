package com.essentia.essentiacatalog.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Essentia — Catalog API")
                        .description("""
                                Public endpoints for browsing the fragrance catalog.

                                No authentication required.
                                Supports filtering by name, brand, parfumer and fragrance note.
                                """)
                        .version("1.0.0"));
    }
}
