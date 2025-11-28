package com.example.Producto;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.example.Producto.model.ProductModel;
import com.example.Producto.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import net.datafaker.Faker;

/**
 * DataLoader: Carga datos de videojuegos de ejemplo usando DataFaker
 */
@Component
public class DataLoader implements CommandLineRunner {
    // Inyección del repositorio de productos
    @Autowired
    private ProductRepository productRepository;
    // Método que se ejecuta al iniciar la aplicación
    @Override
    public void run(String... args) throws Exception {
        // Cargar productos de videojuegos si la BD está vacía
        if (productRepository.count() == 0) {
            // Datos realistas de videojuegos
            String[] juegos = {
                "The Legend of Zelda: Breath of the Wild",
                "Elden Ring",
                "The Legend of Zelda: Tears of the Kingdom",
                "Cyberpunk 2077",
                "Final Fantasy VII Remake",
                "Hogwarts Legacy",
                "Starfield",
                "Forspoken",
                "Baldur's Gate 3",
                "Persona 5 Royal",
                "Final Fantasy XVI",
                "Spider-Man 2",
                "God of War Ragnarök",
                "Gran Turismo 7",
                "Hades"
            };
            // Categorías de consolas
            String[] consolas = {
                "PlayStation 5",
                "Xbox Series X",
                "Xbox Series S",
                "Nintendo Switch",
                "PC",
                "PlayStation 4",
                "Xbox One"
            };
            // Géneros de videojuegos
            String[] generos = {
                "Acción",
                "RPG",
                "Aventura",
                "Carreras",
                "Estrategia",
                "Simulación",
                "Deportes",
                "Terror",
                "Puzzle",
                "Plataforma"
            };
            // Instancia de Faker para generar datos aleatorios
            Faker faker = new Faker();
            
            // Generar 15 videojuegos con datos realistas
            for (int i = 0; i < 15; i++) {
                ProductModel producto = new ProductModel();
                
                // Nombre del juego (sin repetir si es posible)
                String nombreJuego = juegos[i % juegos.length];
                if (i >= juegos.length) {
                    nombreJuego += " - " + faker.company().name();
                }
                producto.setName(nombreJuego);
                
                // Descripción del juego
                String descripcion = "Un emocionante juego de " + generos[i % generos.length].toLowerCase() + 
                                    ". " + faker.lorem().sentence(15);
                producto.setDescription(descripcion);
                
                // Precio realista de videojuegos (39.99 - 79.99)
                double precio = Math.round((39.99 + (faker.number().numberBetween(0, 4000) * 0.01)) * 100.0) / 100.0;
                producto.setPrice(precio);
                
                // Stock aleatorio (5 - 150 unidades)
                producto.setStockQuantity(faker.number().numberBetween(5, 150));
                
                // Categoría: Consola del producto
                producto.setCategory(consolas[i % consolas.length]);
                
                productRepository.save(producto);
            }
            
            System.out.println("listo " + productRepository.count() + " videojuegos cargados exitosamente en MySQL");
            System.out.println(" Datos disponibles en: http://localhost:8080/api/v1/productos");
        }
    }
}
