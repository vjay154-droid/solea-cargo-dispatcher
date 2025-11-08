package no.solea.cargodispatcher.controller;

import no.solea.cargodispatcher.dto.ProductRequestDTO;
import no.solea.cargodispatcher.dto.ProductResponseDTO;
import no.solea.cargodispatcher.service.ProductService;
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
public class ProductControllerTest {
    @Mock
    private ProductService productService;
    @InjectMocks
    private ProductController productController;

    @Test
    void getProducts_shouldReturnList() {
        List<ProductResponseDTO> mockList = List.of(
                new ProductResponseDTO(1L, "Widget", 10.0),
                new ProductResponseDTO(2L, "Gadget", 5.0)
        );

        when(productService.getProductResponseList()).thenReturn(mockList);

        ResponseEntity<List<ProductResponseDTO>> response = productController.getProducts();

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals("Widget", response.getBody().getFirst().name());
    }

    @Test
    void getProductById_shouldReturnProduct() {
        ProductResponseDTO dto = new ProductResponseDTO(1L, "Widget", 10.0);
        when(productService.getProductResponseById(1L)).thenReturn(dto);

        ResponseEntity<ProductResponseDTO> response = productController.getProductById(1L);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Widget", response.getBody().name());
    }

    @Test
    void getProductById_shouldThrowException_whenNotFound() {
        when(productService.getProductResponseById(999L))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        assertThrows(ResponseStatusException.class, () -> productController.getProductById(999L));
    }

    @Test
    void createProduct_shouldReturnCreated() {
        ProductRequestDTO request = new ProductRequestDTO("NewItem", 7.0);
        ProductResponseDTO responseDTO = new ProductResponseDTO(3L, "NewItem", 7.0);

        when(productService.createProductResponse(request)).thenReturn(responseDTO);

        ResponseEntity<ProductResponseDTO> response = productController.createProduct(request);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("NewItem", response.getBody().name());
    }

    @Test
    void updateProduct_shouldReturnUpdated() {
        ProductRequestDTO request = new ProductRequestDTO("UpdatedWidget", 12.0);
        ProductResponseDTO responseDTO = new ProductResponseDTO(1L, "UpdatedWidget", 12.0);

        when(productService.updateProductResponse(1L,request)).thenReturn(responseDTO);

        ResponseEntity<ProductResponseDTO> response = productController.updateProduct(1L,request);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("UpdatedWidget", response.getBody().name());
    }
}
