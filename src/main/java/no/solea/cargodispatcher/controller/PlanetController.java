package no.solea.cargodispatcher.controller;

import lombok.extern.slf4j.Slf4j;
import no.solea.cargodispatcher.dto.PlanetResponseDTO;
import no.solea.cargodispatcher.service.PlanetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for managing planets.
 * Provides endpoints to retrieve all planets or a planet by its ID.
 */
@RestController
@RequestMapping("/planets")
@Slf4j
public class PlanetController {
    private final PlanetService planetService;

    public PlanetController(PlanetService planetService) {
        this.planetService = planetService;
    }

    /**
     * Retrieve all planets.
     * @return List of PlanetResponseDTO wrapped in ResponseEntity with HTTP 200.
     */
    @GetMapping
    public ResponseEntity<List<PlanetResponseDTO>> getPlanets(){
        log.info("Get /planets called");
        return ResponseEntity.ok(
                planetService.getPlanetResponseList()
        );
    }

    /**
     * Retrieve a planet by its ID.
     *
     * @param id The ID of the planet to retrieve.
     * @return PlanetResponseDTO wrapped in ResponseEntity with HTTP 200.
     * @throws org.springframework.web.server.ResponseStatusException if the planet is not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PlanetResponseDTO> getPlanetById(@PathVariable long id){
        log.info("Get /planets/{} called",id);
        return ResponseEntity.ok(
                planetService.getPlanetResponseById(id)
        );
    }
}
