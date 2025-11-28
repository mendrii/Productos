package com.example.Producto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.example.Producto.service.PedidoIntegracionService;

import java.util.Map;
// Controlador para integración con otros microservicios
@RestController
@RequestMapping("/api/v1/integracion")
@Tag(name = "Integración", description = "Endpoints para comunicarse con otros microservicios")
public class IntegracionController {
    
    @Autowired
    private PedidoIntegracionService pedidoIntegracionService;
// Endpoint para obtener todos los pedidos
    @GetMapping("/pedidos")
    @Operation(summary = "Listar todos los pedidos")
    // Recibe el token de autorización en el header
    public ResponseEntity<?> obtenerTodosPedidos(
            @RequestHeader("Authorization") String token
    ) {
        // Imprimir en consola para seguimiento
        System.out.println
        ("Products → Petición para listar órdenes");
        return pedidoIntegracionService.obtenerTodosPedidos(token);
    }
    // Endpoint para obtener un pedido por ID
    @GetMapping("/pedidos/{id}")
    @Operation(summary = "Obtener pedido por ID")
    // Recibe el token de autorización en el header
    public ResponseEntity<?> obtenerPedidoPorId(
            @PathVariable Integer id,
            @RequestHeader("Authorization") String token
    ) {
        // Imprimir en consola para seguimiento
        System.out.println
        ("Products → Petición para orden ID: " + id);
        // Delegar la obtención del pedido al servicio de integración
        return pedidoIntegracionService.obtenerPedidoPorId(id, token);
    }
    // Endpoint para crear un nuevo pedido
    @PostMapping("/pedidos")
    @Operation(summary = "Crear un nuevo pedido")
    public ResponseEntity<?> crearPedido(
            @RequestBody Map<String, Object> pedido,
            @RequestHeader("Authorization") String token
    ) {
        // Imprimir en consola para seguimiento
        System.out.println
        ("Products → Petición para crear orden");
        // Delegar la creación del pedido al servicio de integración
        return pedidoIntegracionService.crearPedido(pedido, token);
    }
}