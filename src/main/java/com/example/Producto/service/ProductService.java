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

    public List<ProductModel> findAll(){
        return productoRepository.findAll();
    }

    public ProductModel findById(int id){
        return productoRepository.findById(id).get();
    }

    public ProductModel save(ProductModel prod){
        return productoRepository.save(prod);
    }

    public ProductModel update(ProductModel prod){
        return productoRepository.save(prod);
    }

    public void delete(int id){
        productoRepository.deleteById(id);
    }
    
}
