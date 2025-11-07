package no.solea.cargodispatcher.dto;

public record OrderItemResponseDTO(
        long productId,
        String productName,
        Integer quantity,
        double productSize
) {
}
