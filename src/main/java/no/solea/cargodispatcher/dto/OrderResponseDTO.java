package no.solea.cargodispatcher.dto;

import java.util.List;

public record OrderResponseDTO(
        Long id,
        String planetName,
        String vehicleName,
        Double totalVolume,
        Double travelTime,
        List<OrderItemResponseDTO> items
) {
}
