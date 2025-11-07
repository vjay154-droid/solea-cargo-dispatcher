package no.solea.cargodispatcher.dto;

public record OrderItemRequestDTO(
        Long productId,
        Integer quantity
) {
}
