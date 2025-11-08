package no.solea.cargodispatcher.service;

import lombok.extern.slf4j.Slf4j;
import no.solea.cargodispatcher.dto.OrderRequestDTO;
import no.solea.cargodispatcher.dto.OrderResponseDTO;
import no.solea.cargodispatcher.mapper.OrderMapper;
import no.solea.cargodispatcher.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Service layer for managing orders.
 * Handles placing orders, calculating total volume, assigning vehicles, and retrieving orders.
 */
@Service
@Slf4j
public class OrderService {
    private final PlanetService planetService;
    private final ProductService productService;
    private final VehicleService vehicleService;

    Long orderId = 1L;
    List<Order> orders = new ArrayList<>();

    public OrderService(PlanetService planetService,
                        ProductService productService,
                        VehicleService vehicleService) {
        this.planetService = planetService;
        this.productService = productService;
        this.vehicleService = vehicleService;
    }

    /**
     * Places a new order.
     *
     * @param orderRequestDTO order request containing planet ID and items
     * @return OrderResponseDTO with assigned vehicle and calculated travel time
     */
    public OrderResponseDTO placeOrder(OrderRequestDTO orderRequestDTO){
        log.info("Placing order {}",orderRequestDTO);
        Order order = OrderMapper.toOrder(orderRequestDTO);

        Planet planet = planetService.getPlanetById(order.getPlanetId());

        List<Product> products = getProducts(order);

        double totalVolume = computeTotalVolume(order.getItems(), products);

        Vehicle assignedVehicle = findAssignedVehicle(totalVolume,planet.getDistance());

        order.setAssignedVehicle(assignedVehicle);
        order.setId(orderId++);
        orders.add(order);

        double travelTime = planet.getDistance() / assignedVehicle.getSpeed();
        order.setTravelTime(travelTime);
        order.setTotalVolume(totalVolume);

        log.info("Order placed successfully {}",order);
        return OrderMapper.toOrderResponseDTO(order,planet,products);
    }

    /**
     * Retrieve all orders.
     *
     * @return list of OrderResponseDTO
     */
    public List<OrderResponseDTO> getOrders(){
        log.info("Getting all orders");
        List<OrderResponseDTO> orderResponseDTOS = orders.stream()
                .map(this::mapOrders)
                .toList();
        log.info("Returning {} orders",orderResponseDTOS.size());
        return orderResponseDTOS;
    }

    /**
     * Retrieve a specific order by ID.
     *
     * @param orderId order ID
     * @return OrderResponseDTO
     */
    public OrderResponseDTO getOrderById(long orderId){
        log.info("Getting order {}",orderId);
        Order order = orders.stream()
                .filter(order1 -> order1.getId() == orderId)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Order not found for id "+orderId));
        log.info("Returning order {}",order);
        return mapOrders(order);
    }

    /**
     * Maps an Order object to OrderResponseDTO.
     */
    private OrderResponseDTO mapOrders(Order order) {
        Planet planet = planetService.getPlanetById(order.getPlanetId());
        List<Product> products = getProducts(order);

        return OrderMapper.toOrderResponseDTO(order,
                planet,
                products);
    }

    /**
     * Fetch all Product objects for the given order items.
     */
    private List<Product> getProducts(Order order){
        List<Product> products = new ArrayList<>();

        for(OrderItem item: order.getItems()){
            Product product = productService
                    .getProductById(item.getProductId());
            products.add(product);
        }

        return products;
    }

    /**
     * Computes total volume of an order.
     */
    private double computeTotalVolume(List<OrderItem> items, List<Product> products){
        double volume = 0.0;
        for (int i=0; i<items.size(); i++){
            volume += items.get(i).getQuantity() * products.get(i).getSize();
        }
        return volume;
    }

    /**
     * Assigns the most optimal vehicle for the order.
     * Chooses the vehicle that can carry the total volume and reach the planet fastest.
     */
    private Vehicle findAssignedVehicle(Double totalVolume,Double distance){
        List<Vehicle> vehicles = vehicleService.getVehicles();
        double maxCapacity = vehicles.stream()
                .mapToDouble(Vehicle::getCapacity)
                .max()
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Vehicle not found"));

        if (totalVolume > maxCapacity){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Order can not be placed because order volume exceeds maximum capacity");
        }

        return vehicles.stream()
                .filter(vehicle -> vehicle.getCapacity() >= totalVolume)
                .min(Comparator.comparingDouble(v -> distance/v.getSpeed()))
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "No suitable vehicle found"));
    }
}
