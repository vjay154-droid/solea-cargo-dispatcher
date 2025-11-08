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

@RestController
@RequestMapping("/planets")
@Slf4j
public class PlanetController {
    private final PlanetService planetService;

    public PlanetController(PlanetService planetService) {
        this.planetService = planetService;
    }

    @GetMapping
    public ResponseEntity<List<PlanetResponseDTO>> getPlanets(){
        log.info("Get /planets called");
        return ResponseEntity.ok(
                planetService.getPlanetResponseList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanetResponseDTO> getPlanetById(@PathVariable long id){
        log.info("Get /planets/{} called",id);
        return ResponseEntity.ok(
                planetService.getPlanetResponseById(id)
        );
    }
}
