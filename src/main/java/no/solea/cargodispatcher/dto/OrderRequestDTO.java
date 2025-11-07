package no.solea.cargodispatcher.dto;

import java.util.List;

public record OrderRequestDTO(
        Long planetId,
        List<OrderItemRequestDTO> orderItemDTOList
) {
}
