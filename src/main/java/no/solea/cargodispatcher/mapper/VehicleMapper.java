package no.solea.cargodispatcher.mapper;

import no.solea.cargodispatcher.dto.VehicleResponseDTO;
import no.solea.cargodispatcher.model.Vehicle;

import java.util.List;

public class VehicleMapper {
    public static VehicleResponseDTO toVehicleResponseDTO(Vehicle vehicle) {
        return new VehicleResponseDTO(
                vehicle.getId(),
                vehicle.getName(),
                vehicle.getSpeed(),
                vehicle.getCapacity()
        );
    }

    public static List<VehicleResponseDTO> toVehicleResponseDTO(List<Vehicle> vehicles) {
        return vehicles.stream()
                .map(VehicleMapper::toVehicleResponseDTO)
                .toList();
    }
}
