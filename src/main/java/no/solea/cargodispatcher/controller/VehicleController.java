package no.solea.cargodispatcher.controller;

import lombok.extern.slf4j.Slf4j;
import no.solea.cargodispatcher.dto.VehicleResponseDTO;
import no.solea.cargodispatcher.service.VehicleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for managing vehicles.
 * Provides endpoints to fetch vehicle information.
 */
@RestController
@RequestMapping("/vehicles")
@Slf4j
public class VehicleController {
    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService){
        this.vehicleService = vehicleService;
    }

    /**
     * Retrieve all vehicles.
     *
     * @return List of VehicleResponseDTO wrapped in ResponseEntity with HTTP 200.
     */
    @GetMapping
    public ResponseEntity<List<VehicleResponseDTO>> getVehicles(){
        log.info("Get /vehicles called");
        return ResponseEntity.ok(
                vehicleService.getVehicleResponseList()
        );
    }

    /**
     * Retrieve a vehicle by its ID.
     *
     * @param id The ID of the vehicle to retrieve.
     * @return VehicleResponseDTO wrapped in ResponseEntity with HTTP 200.
     */
    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponseDTO> getVehicleById(@PathVariable long id){
        log.info("Get /vehicles/{} called",id);
        return ResponseEntity.ok(
                vehicleService.getVehicleResponseById(id)
        );
    }
}
