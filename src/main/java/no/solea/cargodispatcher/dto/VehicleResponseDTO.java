package no.solea.cargodispatcher.dto;

public record VehicleResponseDTO(
        Long id,
        String name,
        Double speed,
        Integer capacity
) {
}
