package no.solea.cargodispatcher.service;

import lombok.extern.slf4j.Slf4j;
import no.solea.cargodispatcher.dto.PlanetResponseDTO;
import no.solea.cargodispatcher.loader.DataLoader;
import no.solea.cargodispatcher.mapper.PlanetMapper;
import no.solea.cargodispatcher.model.Planet;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Service layer for managing planets.
 * Provides operations for retrieving planets and converting them to response DTOs.
 */
@Service
@Slf4j
public class PlanetService {

    private final DataLoader dataLoader;

    public PlanetService(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    /**
     * Retrieve all planets.
     *
     * @return list of Planet objects
     */
    public List<Planet> getPlanets(){
        log.info("Getting planets");
        List<Planet> planets= dataLoader.getPlanets();
        log.info("Fetched {} planets",planets.size());
        return planets;
    }

    /**
     * Retrieve a planet by its ID.
     *
     * @param id planet ID
     * @return Planet object
     * @throws ResponseStatusException if planet not found
     */
    public Planet getPlanetById(long id){
        log.info("Getting planet by id");
        Planet planet = dataLoader.getPlanets().stream()
                .filter(planet1 -> planet1.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Planet not found for id "+id));
        log.info("Found planet {}",planet);
        return planet;
    }

    /**
     * Retrieve all planets as DTOs for API responses.
     *
     * @return list of PlanetResponseDTO
     */
    public List<PlanetResponseDTO> getPlanetResponseList(){
        return PlanetMapper.toPlanetResponseDTOs(getPlanets());
    }

    /**
     * Retrieve a single planet as a DTO by its ID.
     *
     * @param id planet ID
     * @return PlanetResponseDTO
     */
    public PlanetResponseDTO getPlanetResponseById(long id){
        return PlanetMapper.toPlanetResponseDTO(getPlanetById(id));
    }
}
