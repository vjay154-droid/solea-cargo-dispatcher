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

/**
 * Service layer for managing vehicles.
 * Provides operations to fetch vehicles and convert them to DTOs for API responses.
 */
@Service
@Slf4j
public class VehicleService {
    private final DataLoader dataLoader;

    public VehicleService(DataLoader dataLoader){
        this.dataLoader = dataLoader;
    }

    /**
     * Retrieve all vehicles.
     *
     * @return list of Vehicle objects
     */
    public List<Vehicle> getVehicles(){
        log.info("Getting vehicles");
        List<Vehicle> vehicles = dataLoader.getVehicles();
        log.info("Found {} vehicles", vehicles.size());
        return vehicles;
    }

    /**
     * Retrieve a vehicle by its ID.
     *
     * @param id vehicle ID
     * @return Vehicle object
     * @throws ResponseStatusException if vehicle not found
     */
    public Vehicle getVehicleById(long id){
        log.info("Getting vehicle {}", id);

        Vehicle vehicle = dataLoader.getVehicles().stream()
                .filter(vehicle1 -> vehicle1.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Vehicle not found for id "+id));

        log.info("Found vehicle {}", vehicle);
        return vehicle;
    }

    /**
     * Retrieve all vehicles as DTOs.
     *
     * @return list of VehicleResponseDTO
     */
    public List<VehicleResponseDTO> getVehicleResponseList(){
        return VehicleMapper.toVehicleResponseDTO(getVehicles());
    }

    /**
     * Retrieve a vehicle as DTO by its ID.
     *
     * @param id vehicle ID
     * @return VehicleResponseDTO
     */
    public VehicleResponseDTO getVehicleResponseById(long id){
        return VehicleMapper.toVehicleResponseDTO(getVehicleById(id));
    }
}
