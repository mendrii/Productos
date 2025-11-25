package com.example.Producto.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(
    name = "pedidos-service",
    url = "${pedidos.service.url:http://localhost:8083}"  
)
public interface PedidoClient {

    @GetMapping("/api/ordenes")  

    ResponseEntity<Map<String, Object>> obtenerTodosPedidos(
            @RequestHeader("Authorization") String token
    );

    @GetMapping("/api/ordenes/{id}")
    ResponseEntity<Map<String, Object>> obtenerPedidoPorId(
            @PathVariable("id") Integer id,
            @RequestHeader("Authorization") String token
    );

    @PostMapping("/api/ordenes")
    ResponseEntity<Map<String, Object>> crearPedido(
            @RequestBody Map<String, Object> pedido,
            @RequestHeader("Authorization") String token
    );
}