package no.solea.cargodispatcher.controller;

import no.solea.cargodispatcher.dto.OrderItemRequestDTO;
import no.solea.cargodispatcher.dto.OrderItemResponseDTO;
import no.solea.cargodispatcher.dto.OrderRequestDTO;
import no.solea.cargodispatcher.dto.OrderResponseDTO;
import no.solea.cargodispatcher.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {
    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private OrderRequestDTO orderRequest;
    private OrderResponseDTO orderResponse;

    @BeforeEach
    void setUp() {
        orderRequest = new OrderRequestDTO(
                1L,
                List.of(
                        new OrderItemRequestDTO(1L, 10),
                        new OrderItemRequestDTO(2L, 5)
                )
        );

        orderResponse = new OrderResponseDTO(
                1L,
                "Mars",
                "Cheetah Gonzales",
                15.0,
                12.0,
                List.of(
                        new OrderItemResponseDTO(1L,
                                "Package of adult diapers",
                                10,
                                1.0),
                        new OrderItemResponseDTO(2L,
                                "Ten pack clown suit",
                                5,
                                1.0)
                )
        );
    }

    @Test
    void testCreateOrder() {
        when(orderService.placeOrder(orderRequest)).thenReturn(orderResponse);

        OrderResponseDTO response = Objects.requireNonNull(
                orderController.createOrder(orderRequest).getBody(),
                "Response body should not be null"
        );

        assertEquals(1L, response.id());
        assertEquals("Mars", response.planetName());
        assertEquals("Cheetah Gonzales", response.vehicleName());
        assertEquals(15.0, response.totalVolume());
        assertEquals(12.0, response.travelTime());
        assertEquals(2, response.items().size());
        assertEquals("Package of adult diapers", response.items().getFirst().productName());
        assertEquals(10, response.items().getFirst().quantity());
        assertEquals(1.0, response.items().getFirst().productSize());
    }

    @Test
    void testGetOrders() {
        when(orderService.getOrders()).thenReturn(List.of(orderResponse));

        List<OrderResponseDTO> orders = Objects.requireNonNull(
                    orderController.getOrders().getBody(),
                "Response body should not be null"
        );

        assertEquals(1, orders.size());
        OrderResponseDTO first = orders.getFirst();
        assertEquals("Mars", first.planetName());
        assertEquals("Cheetah Gonzales", first.vehicleName());
        assertEquals(2, first.items().size());
        assertEquals("Ten pack clown suit", first.items().get(1).productName());
    }

    @Test
    void testGetOrderById() {
        when(orderService.getOrderById(1L)).thenReturn(orderResponse);

        OrderResponseDTO response = Objects.requireNonNull(
                orderController.getOrderById(1L).getBody(),
                "Response body should not be null"
        );

        assertEquals(1L, response.id());
        assertEquals("Mars", response.planetName());
        assertEquals("Cheetah Gonzales", response.vehicleName());
        assertEquals(2, response.items().size());
        assertEquals(5, response.items().get(1).quantity());
        assertEquals(1.0, response.items().get(1).productSize());
    }

    @Test
    void testCreateOrderWithEmptyItems_shouldThrowException() {
        OrderRequestDTO emptyRequest = new OrderRequestDTO(1L, List.of());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> orderController.createOrder(emptyRequest)
        );

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Order must contain at least one item",
                exception.getReason());
    }
}
