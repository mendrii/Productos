package com.example.Producto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.Producto.client.PedidoClient;

import java.util.Map;

@Service
public class PedidoIntegracionService {
    // Inyección del cliente Feign para comunicarse con el servicio de pedidos
    @Autowired
    private PedidoClient pedidoClient;
    // Obtener todos los pedidos
    public ResponseEntity<?> obtenerTodosPedidos(String token) {
        try {
            // Llamada al servicio de pedidos
            System.out.println(" Products → Llamando a Orders Service");
            ResponseEntity<Map<String, Object>> response = pedidoClient.obtenerTodosPedidos(token);
            System.out.println(" Products → Orders respondió correctamente");
            // Devolver la respuesta recibida
            return response;
        } catch (Exception e) {
            System.err.println(" Products → Error al conectar con Orders: " + e.getMessage());
            // Devolver error genérico
            return ResponseEntity.status(503).body(
                Map.of(
                    "success", false,
                    "message", "Error al conectar con el servicio de Órdenes",
                    "error", e.getMessage()
                )
            );
        }
    }
    // Obtener un pedido por su ID
    public ResponseEntity<?> obtenerPedidoPorId(Integer id, String token) {
        try {
            // Llamada al servicio de pedidos
            System.out.println(" Products → Obteniendo orden ID: " + id);
            // Delegar la obtención del pedido al cliente Feign
            ResponseEntity<Map<String, Object>> response = pedidoClient.obtenerPedidoPorId(id, token);
            System.out.println(" Products → Orden encontrada");
            // Devolver la respuesta recibida
            return response;
        } catch (Exception e) {
            System.err.println(" Products → Error: " + e.getMessage());
            // Devolver error genérico
            return ResponseEntity.status(503).body(
                Map.of(
                    "success", false,
                    "message", "Error al obtener el pedido",
                    "error", e.getMessage()
                )
            );
        }
    }

    // Crear un nuevo pedido
    public ResponseEntity<?> crearPedido(Map<String, Object> pedido, String token) {
        // Llamada al servicio de pedidos
        try {
            System.out.println("Products → Creando nueva orden");
            // Delegar la creación del pedido al cliente Feign
            ResponseEntity<Map<String, Object>> response = pedidoClient.crearPedido(pedido, token);
            // Devolver la respuesta recibida
            System.out.println(" Products → Orden creada exitosamente");
            return response;
            // Capturar errores de comunicación
        } catch (Exception e) {
            System.err.println(" Products → Error al crear orden: " + e.getMessage());
            // Devolver error genérico
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

