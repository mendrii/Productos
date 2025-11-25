package com.example.Producto.service;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.Producto.repository.ProductRepository;
import com.example.Producto.model.ProductModel;
import java.util.List;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productoRepository;

    public List<ProductModel> findAll() {
        System.out.println("ProductService → Buscando todos los productos");
        List<ProductModel> productos = productoRepository.findAll();
        System.out.println("ProductService → Encontrados: " + productos.size());
        return productos;
    }

    public ProductModel findById(int id) {
        System.out.println("ProductService → Buscando producto ID: " + id);
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + id));
    }

    public ProductModel save(ProductModel prod) {
        System.out.println("ProductService → Guardando producto: " + prod.getName());
        ProductModel saved = productoRepository.save(prod);
        System.out.println("ProductService → Producto guardado con ID: " + saved.getId());
        return saved;
    }

    public ProductModel update(ProductModel prod) {
        System.out.println("ProductService → Actualizando producto ID: " + prod.getId());
        return productoRepository.save(prod);
    }

    public void delete(int id) {
        System.out.println("ProductService → Eliminando producto ID: " + id);
        productoRepository.deleteById(id);
        System.out.println("ProductService → Producto eliminado");
    }
}
