package com.example.Producto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.example.Producto.service.PedidoIntegracionService;

/**
 * Controlador para consumir el microservicio remoto de Pedidos mediante Feign
 */
@RestController
@RequestMapping("/api/v1/integracion")
@Tag(name = "Integración", description = "Endpoints para comunicarse con otros microservicios")
public class IntegracionController {

    @Autowired
    private PedidoIntegracionService pedidoIntegracionService;

    /**
     * GET: Obtener todos los pedidos del servicio remoto
     */
    @GetMapping("/pedidos")
    @Operation(summary = "Listar todos los pedidos", description = "Obtiene todos los pedidos del microservicio remoto de Pedidos")
    public ResponseEntity<?> obtenerTodosPedidos() {
        return pedidoIntegracionService.obtenerTodosPedidos();
    }

    /**
     * GET: Obtener un pedido específico por ID del servicio remoto
     */
    @GetMapping("/pedidos/{id}")
    @Operation(summary = "Obtener pedido por ID", description = "Obtiene un pedido específico del microservicio remoto")
    public ResponseEntity<?> obtenerPedidoPorId(@PathVariable Integer id) {
        return pedidoIntegracionService.obtenerPedidoPorId(id);
    }

    /**
     * POST: Crear un nuevo pedido en el servicio remoto
     */
    @PostMapping("/pedidos")
    @Operation(summary = "Crear un nuevo pedido", description = "Crea un nuevo pedido en el microservicio remoto")
    public ResponseEntity<?> crearPedido(@RequestBody Object pedido) {
        return pedidoIntegracionService.crearPedido(pedido);
    }
}
