package no.solea.cargodispatcher.controller;

import no.solea.cargodispatcher.dto.VehicleResponseDTO;
import no.solea.cargodispatcher.service.VehicleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VehicleControllerTest {
    @Mock
    private VehicleService vehicleService;

    @InjectMocks
    private VehicleController vehicleController;

    @Test
    void getVehicles_shouldReturnAllVehicles() {
        List<VehicleResponseDTO> mockVehicles = List.of(
                new VehicleResponseDTO(1L, "Falcon", 100.0, 10),
                new VehicleResponseDTO(2L, "Eagle", 200.0, 20)
        );

        when(vehicleService.getVehicleResponseList()).thenReturn(mockVehicles);

        ResponseEntity<List<VehicleResponseDTO>> response =
                vehicleController.getVehicles();

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals("Falcon", response.getBody().getFirst().name());
    }

    @Test
    void getVehicleById_shouldReturnVehicle() {
        VehicleResponseDTO mockVehicle = new VehicleResponseDTO(1L, "Falcon", 100.0, 15);

        when(vehicleService.getVehicleResponseById(1L)).thenReturn(mockVehicle);

        ResponseEntity<VehicleResponseDTO> response =
                vehicleController.getVehicleById(1L);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Falcon", response.getBody().name());
    }

    @Test
    void getVehicleById_shouldThrow_WhenNotFound() {
        when(vehicleService.getVehicleResponseById(99L))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        assertThrows(ResponseStatusException.class, () -> vehicleController.getVehicleById(99L));
    }
}
