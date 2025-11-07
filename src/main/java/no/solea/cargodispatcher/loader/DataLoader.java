package no.solea.cargodispatcher.loader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import no.solea.cargodispatcher.model.Planet;
import no.solea.cargodispatcher.model.Product;
import no.solea.cargodispatcher.model.Vehicle;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@Getter
public class DataLoader {
    private final ObjectMapper objectMapper = new ObjectMapper();

    private List<Product> products;
    private List<Planet> planets;
    private List<Vehicle> vehicles;

    @PostConstruct
    public void loadData() throws IOException {
        products = loadJSONFile("data/products.json", new TypeReference<>(){});
        planets = loadJSONFile("data/planets.json", new TypeReference<>(){});
        vehicles = loadJSONFile("data/vehicles.json", new TypeReference<>(){});
    }

    private <T> List<T> loadJSONFile(String fileName, TypeReference<List<T>> type) throws IOException {
        ClassPathResource resource = new ClassPathResource(fileName);
        if (!resource.exists()) {
            throw new RuntimeException("JSON file not found in classpath: " + fileName);
        }

        return objectMapper.readValue(resource.getInputStream(), type);
    }
}
