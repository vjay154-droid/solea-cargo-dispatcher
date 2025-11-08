package no.solea.cargodispatcher.dto;

public record OrderItemResponseDTO(
        Long productId,
        String productName,
        Integer quantity,
        Double productSize
) {
}
