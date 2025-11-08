package no.solea.cargodispatcher.mapper;

import no.solea.cargodispatcher.dto.ProductRequestDTO;
import no.solea.cargodispatcher.dto.ProductResponseDTO;
import no.solea.cargodispatcher.dto.ProductUpdateRequestDTO;
import no.solea.cargodispatcher.model.Product;

import java.util.List;

/**
 * Utility class for converting between Product models and Product DTOs.
 */
public class ProductMapper {

    public static ProductResponseDTO toProductResponseDTO(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getSize()
        );
    }

    public static Product toProduct(ProductRequestDTO productRequestDTO) {
        return new Product(
                0L,
                productRequestDTO.name(),
                productRequestDTO.size()
        );
    }

    public static Product toProduct(
            ProductUpdateRequestDTO productUpdateRequestDTO) {
        return new Product(
                0L,
                productUpdateRequestDTO.name(),
                productUpdateRequestDTO.size()
        );
    }

    public static List<ProductResponseDTO> toProductResponseDTO(List<Product> product) {
        return product.stream()
                .map(ProductMapper::toProductResponseDTO)
                .toList();
    }
}
