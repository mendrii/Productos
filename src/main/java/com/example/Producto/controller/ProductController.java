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
    public ResponseEntity<CollectionModel<EntityModel<ProductModel>>> listar() {
        List<ProductModel> productos = productoService.findAll();
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<EntityModel<ProductModel>> productosHateoas = productos.stream()
            .map(producto -> EntityModel.of(producto,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).buscar(producto.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).listar()).withRel("productos")
            ))
            .toList();

        CollectionModel<EntityModel<ProductModel>> collectionModel = CollectionModel.of(productosHateoas,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).listar()).withSelfRel()
        );
        return ResponseEntity.ok(collectionModel);
    }

    @PostMapping
    @Operation(summary = "Guardar un nuevo producto", description = "Crea un nuevo producto en el inventario.")
    public ResponseEntity<EntityModel<ProductModel>> guardar(@RequestBody ProductModel product) {
        ProductModel productonuevo = productoService.save(product);
        EntityModel<ProductModel> productHateoas = EntityModel.of(productonuevo,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).buscar(productonuevo.getId())).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).listar()).withRel("productos")
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(productHateoas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar un producto por ID", description = "Obtiene los detalles de un producto específico por su ID.")
    public ResponseEntity<EntityModel<ProductModel>> buscar(@PathVariable Integer id) {
        try {
            ProductModel product = productoService.findById(id);
            EntityModel<ProductModel> productHateoas = EntityModel.of(product,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).buscar(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).listar()).withRel("productos")
            );
            return ResponseEntity.ok(productHateoas);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

   @PutMapping("/{id}")
    @Operation(summary = "Actualizar un producto", description = "Actualiza los detalles de un producto existente por su ID.")
    public ResponseEntity<EntityModel<ProductModel>> actualizar(@PathVariable Integer id, @RequestBody ProductModel product) {
        try {
            ProductModel prod = productoService.findById(id);
            prod.setId(id);
            prod.setName(product.getName());
            prod.setDescription(product.getDescription());
            prod.setPrice(product.getPrice());
            prod.setStockQuantity(product.getStockQuantity());
            productoService.save(prod);

            EntityModel<ProductModel> productHateoas = EntityModel.of(prod,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).buscar(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).listar()).withRel("productos")
            );
            return ResponseEntity.ok(productHateoas);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
   
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un producto", description = "Elimina un producto del inventario por su ID.")
   public ResponseEntity<?> eliminar(@PathVariable int id){
    try {
            productoService.delete(id);
            return ResponseEntity.noContent().build();
    } catch (Exception e) {
            return ResponseEntity.notFound().build();
    }
}

}
    

