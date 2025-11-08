package no.solea.cargodispatcher.service;

import no.solea.cargodispatcher.dto.OrderItemRequestDTO;
import no.solea.cargodispatcher.dto.OrderRequestDTO;
import no.solea.cargodispatcher.dto.OrderResponseDTO;
import no.solea.cargodispatcher.model.Planet;
import no.solea.cargodispatcher.model.Product;
import no.solea.cargodispatcher.model.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @Mock
    private ProductService productService;

    @Mock
    private PlanetService planetService;

    @Mock
    private VehicleService vehicleService;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    public void setUp() {
        orderService.orders.clear();
        orderService.orderId = 1L;
    }

    @Test
    public void placeOrderSuccessTest() {
        // Arrange Mock Planet
        Planet mars = new Planet(1L, "Mars", 0.52D);
        when(planetService.getPlanetById(1L)).thenReturn(mars);

        // Arrange Mock Products
        Product diapers = new Product(1L, "Adult diapers", 1.0);
        OrderItemRequestDTO item = new OrderItemRequestDTO(1L,10);
        when(productService.getProductById(1L)).thenReturn(diapers);

        // Arrange Mock Vehicles
        Vehicle cheetah = new Vehicle(1L, "Cheetah", 1.3, 20);
        Vehicle rhino = new Vehicle(2L, "Rhino", 0.5, 275);

        when(vehicleService.getVehicles()).thenReturn(List.of(cheetah, rhino));

        OrderRequestDTO request = new OrderRequestDTO(
                1L,
                List.of(item)
        );

        // Act
        OrderResponseDTO response = orderService.placeOrder(request);
        // Assert
        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("Mars", response.planetName());
        assertEquals(10.0, response.totalVolume());
        assertEquals("Cheetah", response.vehicleName()); // Cheetah is the smallest that fit
    }

    @Test
    void shouldPlaceOrderSuccessfullyMultipleProducts() {
        Planet mars = new Planet(1L, "Mars", 0.52);
        when(planetService.getPlanetById(1L)).thenReturn(mars);

        Product diapers = new Product(1L, "Adult diapers", 1.0);
        Product water = new Product(2L, "Water", 2.0);
        when(productService.getProductById(1L)).thenReturn(diapers);
        when(productService.getProductById(2L)).thenReturn(water);

        Vehicle rhino = new Vehicle(2L, "Rhino", 0.5, 275);
        when(vehicleService.getVehicles()).thenReturn(List.of(rhino));

        OrderRequestDTO request = new OrderRequestDTO(1L, List.of(
                new OrderItemRequestDTO(1L, 10), // 10 volume
                new OrderItemRequestDTO(2L, 5)   // 10 volume
        ));

        OrderResponseDTO response = orderService.placeOrder(request);

        assertEquals(20.0, response.totalVolume());
        assertEquals("Rhino", response.vehicleName());
    }

    @Test
    void shouldThrowException_WhenOrderExceedsMaximumCapacity() {
        Planet mars = new Planet(1L, "Mars", 0.52);
        when(planetService.getPlanetById(1L)).thenReturn(mars);

        Product product = new Product(1L, "Fabricator", 40.0);
        OrderItemRequestDTO item = new OrderItemRequestDTO(1L, 10);
        when(productService.getProductById(1L)).thenReturn(product);

        Vehicle small = new Vehicle(1L, "Small", 1.0, 10);
        when(vehicleService.getVehicles()).thenReturn(List.of(small));

        OrderRequestDTO request = new OrderRequestDTO(
                1L,
                List.of(item) // volume = 400
        );

        assertThrows(ResponseStatusException.class, () -> orderService.placeOrder(request));
    }

    @Test
    void shouldThrowException_WhenPlanetNotFound() {
        when(planetService.getPlanetById(999L))
                .thenThrow(new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Planet not found for id 999")
                );

        OrderRequestDTO request = new OrderRequestDTO(999L, List.of());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> orderService.placeOrder(request));

        assertTrue(ex.getMessage().contains("Planet not found for 999"));
    }

    @Test
    void shouldThrowException_WhenProductNotFound() {
        Planet mars = new Planet(1L, "Mars", 0.52);
        when(planetService.getPlanetById(1L)).thenReturn(mars);

        when(productService.getProductById(999L))
                .thenThrow(new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Product not found for id 999")
                );

        OrderRequestDTO request = new OrderRequestDTO(
                1L,
                List.of(new OrderItemRequestDTO(999L, 1))
        );

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> orderService.placeOrder(request));

        assertTrue(ex.getMessage().contains("Product not found for id 999"));
    }

    @Test
    void shouldThrowException_WhenOrderItemsEmpty() {
        Planet mars = new Planet(1L, "Mars", 0.52);
        when(planetService.getPlanetById(1L)).thenReturn(mars);

        OrderRequestDTO request = new OrderRequestDTO(1L, List.of());

        assertThrows(ResponseStatusException.class, () -> orderService.placeOrder(request));
    }

    @Test
    void shouldIncrementOrderId() {
        Planet mars = new Planet(1L, "Mars", 0.52);
        when(planetService.getPlanetById(1L)).thenReturn(mars);

        Product product = new Product(1L, "Item", 1.0);
        when(productService.getProductById(1L)).thenReturn(product);

        Vehicle vehicle = new Vehicle(1L, "Vehicle", 1.0, 100);
        when(vehicleService.getVehicles()).thenReturn(List.of(vehicle));

        OrderRequestDTO request1 = new OrderRequestDTO(
                1L,
                List.of(new OrderItemRequestDTO(1L, 10))
        );
        OrderRequestDTO request2 = new OrderRequestDTO(
                1L,
                List.of(new OrderItemRequestDTO(1L, 20))
        );

        OrderResponseDTO response1 = orderService.placeOrder(request1);
        OrderResponseDTO response2 = orderService.placeOrder(request2);

        assertEquals(1L, response1.id());
        assertEquals(2L, response2.id());
    }
}
