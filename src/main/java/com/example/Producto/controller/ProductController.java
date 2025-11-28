package com.example.Producto.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.Producto.model.ProductModel;
import com.example.Producto.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

@RestController
@RequestMapping("/api/v1/productos")
public class ProductController {

    @Autowired
    private ProductService productoService;

    @GetMapping
    @Operation(summary = "Listar todos los productos", description = "Obtiene una lista de todos los productos disponibles en el inventario.")
      // 1. Obtener todos los productos desde el servicio
    public ResponseEntity<CollectionModel<EntityModel<ProductModel>>> listar() {
        List<ProductModel> productos = productoService.findAll();
         // 2. Si no hay productos, devolver 204 NO CONTENT
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }  
          // 3. Convertir cada producto a EntityModel (agrega links HATEOAS)                 
        List<EntityModel<ProductModel>> productosHateoas = productos.stream()
            .map(producto -> EntityModel.of(producto,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).buscar(producto.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).listar()).withRel("productos")
            ))
            .toList();
               // 4. Envolver la lista en CollectionModel (agrega link a la colección)
        CollectionModel<EntityModel<ProductModel>> collectionModel = CollectionModel.of(productosHateoas,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).listar()).withSelfRel()
        );
             // 5. Devolver 200 OK con la colección
        return ResponseEntity.ok(collectionModel);
    }

    @PostMapping
    @Operation(summary = "Guardar un nuevo producto", description = "Crea un nuevo producto en el inventario.")
    public ResponseEntity<EntityModel<ProductModel>> guardar(@RequestBody ProductModel product) {
          // 1. Guardar el producto usando el servicio (genera ID automáticamente)
        ProductModel productonuevo = productoService.save(product);
          // 2. Crear EntityModel con links HATEOAS
        EntityModel<ProductModel> productHateoas = EntityModel.of(productonuevo,
               // Link al producto recién creado
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).buscar(productonuevo.getId())).withSelfRel(),
               // Link a todos los productos
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).listar()).withRel("productos")
        );
           // 3. Devolver 201 CREATED con el producto creado
        return ResponseEntity.status(HttpStatus.CREATED).body(productHateoas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar un producto por ID", description = "Obtiene los detalles de un producto específico por su ID.")
    public ResponseEntity<EntityModel<ProductModel>> buscar(@PathVariable Integer id) {
        try {
            // 1. Buscar el producto por ID usando el servicio
            ProductModel product = productoService.findById(id);
            // 2. Crear EntityModel con links HATEOAS
            EntityModel<ProductModel> productHateoas = EntityModel.of(product,
                     // Link al producto recién creado  
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).buscar(id)).withSelfRel(),
                  // Link a todos los productos
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).listar()).withRel("productos")
            );
            // 3. Devolver 201 CREATED con el producto creado
            return ResponseEntity.ok(productHateoas);
        } catch (Exception e) {
            System.err.println("Error buscando producto ID: " + id + " -> " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un producto", description = "Actualiza los detalles de un producto existente por su ID.")
    public ResponseEntity<EntityModel<ProductModel>> actualizar(@PathVariable Integer id, @RequestBody ProductModel product) {
           // 1. Buscar el producto existente (lanza excepción si no existe)
        try {
            ProductModel prod = productoService.findById(id);
                // 2. Actualizar todos los campos con los nuevos valores
            prod.setId(id);
            prod.setName(product.getName());
            prod.setDescription(product.getDescription());
            prod.setPrice(product.getPrice());
            prod.setStockQuantity(product.getStockQuantity());
            prod.setCategory(product.getCategory()); // ¡AGREGADO! Este campo faltaba
            
               // 3. Guardar los cambios
            ProductModel updated = productoService.save(prod);
              // 4. Crear EntityModel con links HATEOAS
            EntityModel<ProductModel> productHateoas = EntityModel.of(updated,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).buscar(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).listar()).withRel("productos")
            );
            // 5. Devolver 200 OK con el producto actualizado
            return ResponseEntity.ok(productHateoas);
        } catch (RuntimeException e) {
            // Manejar excepción si el producto no existe
            System.err.println("Error actualizando producto ID: " + id + " -> " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            // Manejar cualquier otra excepción inesperada
            System.err.println("Error inesperado al actualizar ID: " + id + " -> " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un producto", description = "Elimina un producto del inventario por su ID.")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            // 1. Eliminar el producto (lanza excepción si no existe)
            productoService.delete(id);
             // 2. Devolver 204 NO CONTENT (eliminación exitosa, sin body)
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
                 // 3. Error de negocio (producto no encontrado) -> 404
            System.err.println("Error eliminando producto ID: " + id + " -> " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
                // 4. Error inesperado del sistema -> 500
            System.err.println("Error inesperado al eliminar ID: " + id + " -> " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}

// ...existing code...
    

