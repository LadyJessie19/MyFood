package com.spring.myfood.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.*;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfiguration implements WebMvcConfigurer {

        @Bean
        public OpenAPI customOpenAPI() {
                return new OpenAPI()
                                .info(new Info()
                                                .title("My Food - Ranking System")
                                                .description("This documentation comprises all endpoints for the My Food application. My Food is a fictional app that implements a ranking system. The purpose of this project is to showcase the most searched foods and their associated scores within the fictitious app named MyFood.")
                                                .version("1.1")
                                                .contact(new Contact().name("GitHub Repository")
                                                                .url("https://github.com/LadyJessie19/MyFood")));
        }

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/webjars/**")
                                .addResourceLocations("classpath:/META-INF/resources/webjars/");
                registry.addResourceHandler("swagger-ui.html")
                                .addResourceLocations("classpath:/META-INF/resources/");
        }
}