package com.example.Producto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.Producto.client.PedidoClient;

/**
 * Servicio para interactuar con el microservicio remoto de Pedidos mediante Feign
 */
@Service
public class PedidoIntegracionService {

    @Autowired
    private PedidoClient pedidoClient;

    /**
     * Obtiene todos los pedidos del servicio remoto
     */
    public ResponseEntity<?> obtenerTodosPedidos() {
        try {
            return pedidoClient.obtenerTodosPedidos();
        } catch (Exception e) {
            return ResponseEntity.status(503).body("Error al conectar con el servicio de Pedidos: " + e.getMessage());
        }
    }

    /**
     * Obtiene un pedido específico por ID desde el servicio remoto
     */
    public ResponseEntity<?> obtenerPedidoPorId(Integer id) {
        try {
            return pedidoClient.obtenerPedidoPorId(id);
        } catch (Exception e) {
            return ResponseEntity.status(503).body("Error al obtener el pedido: " + e.getMessage());
        }
    }

    /**
     * Crea un nuevo pedido en el servicio remoto
     */
    public ResponseEntity<?> crearPedido(Object pedido) {
        try {
            return pedidoClient.crearPedido(pedido);
        } catch (Exception e) {
            return ResponseEntity.status(503).body("Error al crear el pedido: " + e.getMessage());
        }
    }
}
