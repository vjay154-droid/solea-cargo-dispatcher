package no.solea.cargodispatcher.service;

import no.solea.cargodispatcher.dto.VehicleResponseDTO;
import no.solea.cargodispatcher.loader.DataLoader;
import no.solea.cargodispatcher.mapper.VehicleMapper;
import no.solea.cargodispatcher.model.Vehicle;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class VehicleService {
    private final DataLoader dataLoader;

    public VehicleService(DataLoader dataLoader){
        this.dataLoader = dataLoader;
    }

    public List<Vehicle> getVehicles(){
        return dataLoader.getVehicles();
    }

    public Vehicle getVehicleById(long id){
        return dataLoader.getVehicles().stream()
                .filter(vehicle -> vehicle.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Vehicle not found for id "+id));
    }

    public List<VehicleResponseDTO> getVehicleResponseList(){
        return VehicleMapper.toVehicleResponseDTO(getVehicles());
    }

    public VehicleResponseDTO getVehicleResponseById(long id){
        return VehicleMapper.toVehicleResponseDTO(getVehicleById(id));
    }
}
