package com.spring.myfood.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfiguration {
    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().addSecurityItem(new SecurityRequirement().addList("Header Authorization"))
                .components(new Components().addSecuritySchemes("Authorization", createAPIKeyScheme()))
                .info(new Info().title("MyFood - Ranking System")
                        .description(
                                "The purpose of this project is to demostrate the most searched foods of an fictitious app called MyFood")
                        .version("1.1")
                        .license(new License().name("GitHub Repository")
                                .url("https://github.com/LadyJessie19/MyFood")));
    }
}
