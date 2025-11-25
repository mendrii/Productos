package com.example.Producto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.Producto.client.PedidoClient;

import java.util.Map;

@Service
public class PedidoIntegracionService {

    @Autowired
    private PedidoClient pedidoClient;

    public ResponseEntity<?> obtenerTodosPedidos(String token) {
        try {
            System.out.println(" Products → Llamando a Orders Service");
            ResponseEntity<Map<String, Object>> response = pedidoClient.obtenerTodosPedidos(token);
            System.out.println(" Products → Orders respondió correctamente");
            return response;
        } catch (Exception e) {
            System.err.println(" Products → Error al conectar con Orders: " + e.getMessage());
            return ResponseEntity.status(503).body(
                Map.of(
                    "success", false,
                    "message", "Error al conectar con el servicio de Órdenes",
                    "error", e.getMessage()
                )
            );
        }
    }

    public ResponseEntity<?> obtenerPedidoPorId(Integer id, String token) {
        try {
            System.out.println(" Products → Obteniendo orden ID: " + id);
            ResponseEntity<Map<String, Object>> response = pedidoClient.obtenerPedidoPorId(id, token);
            System.out.println(" Products → Orden encontrada");
            return response;
        } catch (Exception e) {
            System.err.println(" Products → Error: " + e.getMessage());
            return ResponseEntity.status(503).body(
                Map.of(
                    "success", false,
                    "message", "Error al obtener el pedido",
                    "error", e.getMessage()
                )
            );
        }
    }

    public ResponseEntity<?> crearPedido(Map<String, Object> pedido, String token) {
        try {
            System.out.println("Products → Creando nueva orden");
            ResponseEntity<Map<String, Object>> response = pedidoClient.crearPedido(pedido, token);
            System.out.println(" Products → Orden creada exitosamente");
            return response;
        } catch (Exception e) {
            System.err.println(" Products → Error al crear orden: " + e.getMessage());
            return ResponseEntity.status(503).body(
                Map.of(
                    "success", false,
                    "message", "Error al crear el pedido",
                    "error", e.getMessage()
                )
            );
        }
    }
}

