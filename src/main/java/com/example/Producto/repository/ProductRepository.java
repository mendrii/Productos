package com.example.Producto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Producto.model.ProductModel;

@Repository
// Repositorio JPA para la entidad Producto
public interface ProductRepository extends JpaRepository<ProductModel,Integer > {
    
}
