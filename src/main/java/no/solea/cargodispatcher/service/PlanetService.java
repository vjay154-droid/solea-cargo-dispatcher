package no.solea.cargodispatcher.service;

import no.solea.cargodispatcher.dto.PlanetResponseDTO;
import no.solea.cargodispatcher.loader.DataLoader;
import no.solea.cargodispatcher.mapper.PlanetMapper;
import no.solea.cargodispatcher.model.Planet;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PlanetService {

    private final DataLoader dataLoader;

    public PlanetService(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    public List<Planet> getPlanets(){
        return dataLoader.getPlanets();
    }

    public Planet getPlanetById(long id){
        return dataLoader.getPlanets().stream()
                .filter(planet -> planet.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Planet not found for id "+id));
    }

    public List<PlanetResponseDTO> getPlanetResponseList(){
        return PlanetMapper.toPlanetResponseDTOs(getPlanets());
    }

    public PlanetResponseDTO getPlanetResponseById(long id){
        return PlanetMapper.toPlanetResponseDTO(getPlanetById(id));
    }
}
