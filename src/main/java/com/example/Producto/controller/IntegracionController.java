package com.example.Producto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.example.Producto.service.PedidoIntegracionService;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/integracion")
@Tag(name = "Integración", description = "Endpoints para comunicarse con otros microservicios")
public class IntegracionController {

    @Autowired
    private PedidoIntegracionService pedidoIntegracionService;

    @GetMapping("/pedidos")
    @Operation(summary = "Listar todos los pedidos")
    public ResponseEntity<?> obtenerTodosPedidos(
            @RequestHeader("Authorization") String token
    ) {
        System.out.println
        ("Products → Petición para listar órdenes");
        return pedidoIntegracionService.obtenerTodosPedidos(token);
    }

    @GetMapping("/pedidos/{id}")
    @Operation(summary = "Obtener pedido por ID")
    public ResponseEntity<?> obtenerPedidoPorId(
            @PathVariable Integer id,
            @RequestHeader("Authorization") String token
    ) {
        System.out.println
        ("Products → Petición para orden ID: " + id);
        return pedidoIntegracionService.obtenerPedidoPorId(id, token);
    }

    @PostMapping("/pedidos")
    @Operation(summary = "Crear un nuevo pedido")
    public ResponseEntity<?> crearPedido(
            @RequestBody Map<String, Object> pedido,
            @RequestHeader("Authorization") String token
    ) {
        System.out.println
        ("Products → Petición para crear orden");
        return pedidoIntegracionService.crearPedido(pedido, token);
    }
}