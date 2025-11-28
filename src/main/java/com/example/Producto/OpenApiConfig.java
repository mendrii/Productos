package com.example.Producto;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Configuración de OpenAPI para la documentación de la API
@Configuration
public class OpenApiConfig {
    // Definición del bean de OpenAPI con información básica
    @Bean
    // Configura la información de la API
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("API de Gestión de Inventario")
                .version("1.0")
                .description("Documentación de la API para el sistema de gestión de inventario"));
    }
}
