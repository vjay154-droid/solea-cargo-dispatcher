package no.solea.cargodispatcher.controller;

import no.solea.cargodispatcher.dto.VehicleResponseDTO;
import no.solea.cargodispatcher.service.VehicleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/vehicles")
public class VehicleController {
    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService){
        this.vehicleService = vehicleService;
    }

    @GetMapping
    public ResponseEntity<List<VehicleResponseDTO>> getVehicles(){
        return ResponseEntity.ok(
                vehicleService.getVehicleResponseList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponseDTO> getVehicleById(@PathVariable long id){
        return ResponseEntity.ok(
                vehicleService.getVehicleResponseById(id)
        );
    }
}
