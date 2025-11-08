package no.solea.cargodispatcher.service;

import no.solea.cargodispatcher.dto.VehicleResponseDTO;
import no.solea.cargodispatcher.loader.DataLoader;
import no.solea.cargodispatcher.model.Vehicle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VehicleServiceTest {
    @Mock
    private DataLoader dataLoader;

    @InjectMocks
    private VehicleService vehicleService;

    @Test
    void getVehicles_shouldReturnList() {
        List<Vehicle> mockVehicles = List.of(
                new Vehicle(1L, "Falcon", 100.0, 15),
                new Vehicle(2L, "Cheetah", 200.0, 20)
        );

        when(dataLoader.getVehicles()).thenReturn(mockVehicles);

        List<VehicleResponseDTO> result = vehicleService.getVehicleResponseList();

        assertEquals(2, result.size());
        assertEquals("Falcon", result.getFirst().name());
    }

    @Test
    void getVehicleById_shouldReturnVehicle() {
        List<Vehicle> mockVehicles = List.of(
                new Vehicle(1L, "Falcon", 100.0, 15)
        );

        when(dataLoader.getVehicles()).thenReturn(mockVehicles);

        VehicleResponseDTO result = vehicleService.getVehicleResponseById(1L);

        assertEquals("Falcon", result.name());
    }

    @Test
    void getVehicleById_shouldThrow_WhenNotFound() {
        when(dataLoader.getVehicles()).thenReturn(List.of());

        assertThrows(ResponseStatusException.class,
                () -> vehicleService.getVehicleResponseById(99L));
    }
}
