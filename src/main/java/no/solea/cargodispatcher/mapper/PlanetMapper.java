package no.solea.cargodispatcher.mapper;

import no.solea.cargodispatcher.dto.PlanetResponseDTO;
import no.solea.cargodispatcher.model.Planet;

import java.util.List;

/**
 * Utility class for converting between Planet models and Planet DTOs.
 */
public class PlanetMapper {

    public static PlanetResponseDTO toPlanetResponseDTO(Planet planet){
        return new PlanetResponseDTO(
                planet.getId(),
                planet.getName(),
                planet.getDistance()
        );
    }

    public static List<PlanetResponseDTO> toPlanetResponseDTOs(List<Planet> planets){
        return planets.stream()
                .map(PlanetMapper::toPlanetResponseDTO)
                .toList();
    }
}
