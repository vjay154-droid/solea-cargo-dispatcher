package no.solea.cargodispatcher.dto;

import jakarta.validation.constraints.AssertTrue;

public record ProductUpdateRequestDTO(
        String name,
        Double size
) {
    @AssertTrue(message = "At least one field (name or size) must be provided for update")
    @SuppressWarnings("unused")
    public boolean isValid() {
        return name != null || size != null;
    }
}
