package no.solea.cargodispatcher.service;

import lombok.extern.slf4j.Slf4j;
import no.solea.cargodispatcher.dto.VehicleResponseDTO;
import no.solea.cargodispatcher.loader.DataLoader;
import no.solea.cargodispatcher.mapper.VehicleMapper;
import no.solea.cargodispatcher.model.Vehicle;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Slf4j
public class VehicleService {
    private final DataLoader dataLoader;

    public VehicleService(DataLoader dataLoader){
        this.dataLoader = dataLoader;
    }

    public List<Vehicle> getVehicles(){
        log.info("Getting vehicles");
        List<Vehicle> vehicles = dataLoader.getVehicles();
        log.info("Found {} vehicles", vehicles.size());
        return vehicles;
    }

    public Vehicle getVehicleById(long id){
        log.info("Getting vehicle {}", id);

        Vehicle vehicle = dataLoader.getVehicles().stream()
                .filter(vehicle1 -> vehicle1.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Vehicle not found for id "+id));

        log.info("Vehicle {}", vehicle);
        return vehicle;
    }

    public List<VehicleResponseDTO> getVehicleResponseList(){
        return VehicleMapper.toVehicleResponseDTO(getVehicles());
    }

    public VehicleResponseDTO getVehicleResponseById(long id){
        return VehicleMapper.toVehicleResponseDTO(getVehicleById(id));
    }
}
