package no.solea.cargodispatcher.controller;

import no.solea.cargodispatcher.dto.PlanetResponseDTO;
import no.solea.cargodispatcher.service.PlanetService;
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
public class PlanetControllerTest {

    @Mock
    private PlanetService planetService;

    @InjectMocks
    private PlanetController planetController;

    @Test
    void getPlanets_shouldReturnListOfPlanets() {
        List<PlanetResponseDTO> mockResponse = List.of(
                new PlanetResponseDTO(1L, "Mars", 0.52),
                new PlanetResponseDTO(2L, "Venus", 0.8)
        );

        when(planetService.getPlanetResponseList()).thenReturn(mockResponse);

        ResponseEntity<List<PlanetResponseDTO>> response = planetController.getPlanets();

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals("Mars", response.getBody().getFirst().name());
    }

    @Test
    void getPlanetById_shouldReturnPlanet() {
        PlanetResponseDTO mockResponse = new PlanetResponseDTO(1L, "Mars", 0.52);

        when(planetService.getPlanetResponseById(1L)).thenReturn(mockResponse);

        ResponseEntity<PlanetResponseDTO> response = planetController.getPlanetById(1L);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Mars", response.getBody().name());
    }

    @Test
    void getPlanetById_shouldThrowExceptionWhenNotFound() {
        when(planetService.getPlanetResponseById(999L))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        assertThrows(ResponseStatusException.class, () -> planetController.getPlanetById(999L));
    }
}
