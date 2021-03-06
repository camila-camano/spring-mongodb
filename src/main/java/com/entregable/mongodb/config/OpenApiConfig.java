package com.entregable.mongodb.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI config() {
        return new OpenAPI()
                .components(new Components())
//                .paths(RequestHandleS)
                .info(info());
    }

    private Info info() {
        return new Info()
                .title("Coderhouse API")
                .description("Este servicio es un ejemplo de usar OpenAPI en Spring Boot");
    }

}