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

@Service
@Slf4j
public class PlanetService {

    private final DataLoader dataLoader;

    public PlanetService(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    public List<Planet> getPlanets(){
        log.info("Getting planets");
        List<Planet> planets= dataLoader.getPlanets();
        log.info("Get planets done {}",planets);
        return planets;
    }

    public Planet getPlanetById(long id){
        log.info("Getting planet by id");
        Planet planet = dataLoader.getPlanets().stream()
                .filter(planet1 -> planet1.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Planet not found for id "+id));
        log.info("Get planet by id done {}",planet);
        return planet;
    }

    public List<PlanetResponseDTO> getPlanetResponseList(){
        return PlanetMapper.toPlanetResponseDTOs(getPlanets());
    }

    public PlanetResponseDTO getPlanetResponseById(long id){
        return PlanetMapper.toPlanetResponseDTO(getPlanetById(id));
    }
}
