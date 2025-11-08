package no.solea.cargodispatcher.service;

import no.solea.cargodispatcher.dto.ProductRequestDTO;
import no.solea.cargodispatcher.dto.ProductResponseDTO;
import no.solea.cargodispatcher.loader.DataLoader;
import no.solea.cargodispatcher.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private DataLoader dataLoader;

    @InjectMocks
    private ProductService productService;

    private List<Product> mockProducts;

    @BeforeEach
    void setUp() {
        mockProducts = new ArrayList<>();
        mockProducts.add(new Product(1L, "Widget", 10.0));
        mockProducts.add(new Product(2L, "Gadget", 5.0));

        when(dataLoader.getProducts()).thenReturn(mockProducts);
    }

    @Test
    void getProducts_shouldReturnAllProducts() {
        List<Product> products = productService.getProducts();
        assertEquals(2, products.size());
        assertEquals("Widget", products.getFirst().getName());
    }

    @Test
    void getProductById_shouldReturnProduct_whenExists() {
        Product product = productService.getProductById(1L);
        assertEquals("Widget", product.getName());
    }

    @Test
    void getProductById_shouldThrowException_whenNotFound() {
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> productService.getProductById(999L));
        assertTrue(ex.getMessage().contains("Product not found for id"));
    }

    @Test
    void createProduct_shouldAddNewProduct() {
        Product newProduct = new Product(null, "NewItem", 7.0);
        Product created = productService.createProduct(newProduct);

        assertNotNull(created.getId());
        assertEquals("NewItem", created.getName());
        assertEquals(3, mockProducts.size());
    }

    @Test
    void updateProduct_shouldUpdateExistingProduct() {
        Product updated = new Product(1L, "UpdatedWidget", 12.0);
        Product result = productService.updateProduct(updated);

        assertEquals("UpdatedWidget", result.getName());
        assertEquals(12.0, result.getSize());
    }

    @Test
    void updateProduct_shouldThrowException_whenNotFound() {
        Product updated = new Product(999L, "NonExistent", 1.0);

        assertThrows(ResponseStatusException.class,
                () -> productService.updateProduct(updated));
    }

    @Test
    void getProductResponseById_shouldReturnDTO() {
        ProductResponseDTO dto = productService.getProductResponseById(1L);
        assertEquals("Widget", dto.name());
    }

    @Test
    void getProductResponseList_shouldReturnDTOList() {
        List<ProductResponseDTO> dtoList = productService.getProductResponseList();
        assertEquals(2, dtoList.size());
        assertEquals("Widget", dtoList.getFirst().name());
    }

    @Test
    void createProductResponse_shouldReturnDTO() {
        ProductRequestDTO request = new ProductRequestDTO("NewItem", 7.0);
        ProductResponseDTO dto = productService.createProductResponse(request);

        assertEquals("NewItem", dto.name());
    }

    @Test
    void updateProductResponse_shouldReturnDTO() {
        ProductRequestDTO request = new ProductRequestDTO("UpdatedWidget", 12.0);
        ProductResponseDTO dto = productService.updateProductResponse(1L,request);

        assertEquals(1L,dto.id());
        assertEquals("UpdatedWidget", dto.name());
        assertEquals(12.0, dto.size());
    }
}
