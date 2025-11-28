package com.example.Producto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

// Clase principal de la aplicación Spring Boot
@SpringBootApplication
@EnableFeignClients
public class ProductoApplication {

	// Método principal que inicia la aplicación
	public static void main(String[] args) {
		// Ejecuta la aplicación Spring Boot
		SpringApplication.run(ProductoApplication.class, args);
	}

}
