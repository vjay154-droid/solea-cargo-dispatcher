package no.solea.cargodispatcher.controller;

import lombok.extern.slf4j.Slf4j;
import no.solea.cargodispatcher.dto.OrderRequestDTO;
import no.solea.cargodispatcher.dto.OrderResponseDTO;
import no.solea.cargodispatcher.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * REST controller for managing cargo orders.
 * Provides endpoints for creating, retrieving, and listing orders.
 */
@RestController
@RequestMapping("/orders")
@Slf4j
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Retrieve all orders.
     * @return List of OrderResponseDTO wrapped in ResponseEntity with HTTP 200.
     */
    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getOrders(){
        log.info("Get /orders called");
        return ResponseEntity.ok(orderService.getOrders());
    }

    /**
     * Retrieve a single order by ID.
     *
     * @param id Order ID to fetch
     * @return OrderResponseDTO wrapped in ResponseEntity with HTTP 200
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable long id){
        log.info("Get /orders/{} called",id);
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    /**
     * Place a new order.
     * Validates that the order contains at least one item.
     *
     * @param orderRequestDTO OrderRequestDTO with order items and target planet
     * @return Created OrderResponseDTO wrapped in ResponseEntity with HTTP 201
     * @throws ResponseStatusException if order item list is empty or null
     */
    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO orderRequestDTO){
        log.info("Post /orders called with : {}",orderRequestDTO);
        if (orderRequestDTO.orderItemDTOList() == null || orderRequestDTO.orderItemDTOList().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Order must contain at least one item");
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.placeOrder(orderRequestDTO));
    }
}
