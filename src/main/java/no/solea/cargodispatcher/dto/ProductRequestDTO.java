package no.solea.cargodispatcher.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductRequestDTO(
        @NotBlank(message = "Product name is required")
        String name,
        @NotNull(message = "Product size is required")
        @Min(value = 0, message = "Product size must be greater than 0")
        Double size
) {
}
