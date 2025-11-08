package no.solea.cargodispatcher.service;

import no.solea.cargodispatcher.loader.DataLoader;
import no.solea.cargodispatcher.model.Planet;
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
public class PlanetServiceTest {
    @Mock
    private DataLoader dataLoader;

    @InjectMocks
    private PlanetService planetService;

    @Test
    void getPlanets_shouldReturnAllPlanets() {
        List<Planet> mockPlanets = List.of(
                new Planet(1L, "Mars", 0.52),
                new Planet(2L, "Jupiter", 1.5)
        );

        when(dataLoader.getPlanets()).thenReturn(mockPlanets);

        List<Planet> result = planetService.getPlanets();

        assertEquals(2, result.size());
        assertEquals("Mars", result.getFirst().getName());
    }

    @Test
    void getPlanetById_shouldReturnPlanet_whenExists() {
        List<Planet> mockPlanets = List.of(
                new Planet(1L, "Mars", 0.52),
                new Planet(2L, "Jupiter", 1.5)
        );

        when(dataLoader.getPlanets()).thenReturn(mockPlanets);

        Planet result = planetService.getPlanetById(1L);

        assertEquals("Mars", result.getName());
    }

    @Test
    void getPlanetById_shouldThrowException_whenNotFound() {
        when(dataLoader.getPlanets()).thenReturn(List.of());

        ResponseStatusException ex =
                assertThrows(ResponseStatusException.class, () -> planetService.getPlanetById(999L));

        assertTrue(ex.getMessage().contains("Planet not found for id"));
    }
}
