package com.example.Producto.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Cliente Feign para comunicarse con el microservicio de Pedidos.
 * Reemplaza 'pedidos' y la URL con los datos de tu servicio remoto.
 */
@FeignClient(
    name = "pedidos-service",
    url = "${pedidos.service.url:http://localhost:8081}"
)
public interface PedidoClient {

    /**
     * Obtiene todos los pedidos del servicio remoto
     */
    @GetMapping("/api/v1/pedidos")
    ResponseEntity<?> obtenerTodosPedidos();

    /**
     * Obtiene un pedido por ID del servicio remoto
     */
    @GetMapping("/api/v1/pedidos/{id}")
    ResponseEntity<?> obtenerPedidoPorId(@PathVariable("id") Integer id);

    /**
     * Crea un nuevo pedido en el servicio remoto
     */
    @PostMapping("/api/v1/pedidos")
    ResponseEntity<?> crearPedido(@RequestBody Object pedido);
}
