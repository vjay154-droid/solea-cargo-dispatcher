package no.solea.cargodispatcher.mapper;

import no.solea.cargodispatcher.dto.OrderItemResponseDTO;
import no.solea.cargodispatcher.dto.OrderRequestDTO;
import no.solea.cargodispatcher.dto.OrderResponseDTO;
import no.solea.cargodispatcher.model.Order;
import no.solea.cargodispatcher.model.OrderItem;
import no.solea.cargodispatcher.model.Planet;
import no.solea.cargodispatcher.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

public class OrderMapper {

    public static Order toOrder(OrderRequestDTO orderRequestDTO){
        List<OrderItem> items = orderRequestDTO.orderItemDTOList().stream()
                .map(item -> new OrderItem(item.productId(), item.quantity()))
                .toList();

        return new Order(
                0L,
                orderRequestDTO.planetId(),
                0.0,
                0.0,
                items,
                null
        );
    }

    public static OrderResponseDTO toOrderResponseDTO(Order order,
                                                      Planet planet,
                                                      List<Product> products){
        List<OrderItemResponseDTO> itemResponseDTOS = order.getItems().stream()
                .map(orderItem -> {
                    Product product = products.stream()
                            .filter(product1 -> Objects.equals(product1.getId(), orderItem.getProductId()))
                            .findFirst()
                            .orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                    "Product not found for id " + orderItem.getProductId()));
                    return new OrderItemResponseDTO(
                            product.getId(),
                            product.getName(),
                            orderItem.getQuantity(),
                            product.getSize()
                    );
                })
                .toList();

        return new OrderResponseDTO(order.getId(),
                planet.getName(),
                order.getAssignedVehicle().getName(),
                order.getTotalVolume(),
                order.getTravelTime(),
                itemResponseDTOS);
    }
}
