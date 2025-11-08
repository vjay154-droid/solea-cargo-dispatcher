package no.solea.cargodispatcher.service;

import lombok.extern.slf4j.Slf4j;
import no.solea.cargodispatcher.dto.ProductRequestDTO;
import no.solea.cargodispatcher.dto.ProductResponseDTO;
import no.solea.cargodispatcher.dto.ProductUpdateRequestDTO;
import no.solea.cargodispatcher.loader.DataLoader;
import no.solea.cargodispatcher.mapper.ProductMapper;
import no.solea.cargodispatcher.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Service layer for managing products.
 * Provides operations for retrieving, creating, and updating products,
 * and converting them to ProductResponseDTO for API responses.
 */
@Service
@Slf4j
public class ProductService {
    private final DataLoader dataLoader;

    public ProductService(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    /**
     * Retrieve all products.
     *
     * @return list of Product objects
     */
    public List<Product> getProducts(){
        log.info("Fetching all products");
        List<Product> products = dataLoader.getProducts();
        log.info("Fetched {} products", products.size());
        return products;
    }

    /**
     * Retrieve a product by its ID.
     *
     * @param id product ID
     * @return Product object
     * @throws ResponseStatusException if product not found
     */
    public Product getProductById(long id){
        log.info("Getting product by id");

        Product product = dataLoader.getProducts().stream()
                .filter(product1 -> product1.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Product not found for id "+id));

        log.info("Found product by id done {}",product);
        return product;
    }

    /**
     * Create a new product.
     *
     * @param product Product object to create
     * @return created Product object
     */
    public Product createProduct(Product product){
        log.info("Creating new product {}", product);
        List<Product> products = dataLoader.getProducts();

        long newId = products.stream()
                .mapToLong(Product::getId)
                .max()
                .orElse(0) + 1;
        product.setId(newId);

        products.add(product);

        log.info("Created new product {}", product);
        return product;
    }

    /**
     * Update an existing product.
     *
     * @param product Product object containing updated fields
     * @return updated Product object
     * @throws ResponseStatusException if product not found
     */
    public Product updateProduct(Product product){
        log.info("Updating product {}", product);

        Product existingProduct = getProductById(product.getId());

        if (product.getName() != null)
            existingProduct.setName(product.getName());
        if (product.getSize() != null)
            existingProduct.setSize(product.getSize());

        log.info("Updated product {}", existingProduct);
        return existingProduct;
    }

    /**
     * Retrieve a product as a DTO by its ID.
     *
     * @param id product ID
     * @return ProductResponseDTO
     */
    public ProductResponseDTO getProductResponseById(long id){
        return ProductMapper.toProductResponseDTO(getProductById(id));
    }

    /**
     * Retrieve all products as DTOs.
     *
     * @return list of ProductResponseDTO
     */
    public List<ProductResponseDTO> getProductResponseList(){
        return ProductMapper.toProductResponseDTO(getProducts());
    }

    /**
     * Create a new product from a request DTO.
     *
     * @param productRequestDTO request data
     * @return ProductResponseDTO
     */
    public ProductResponseDTO createProductResponse(ProductRequestDTO productRequestDTO){
        Product product = ProductMapper.toProduct(productRequestDTO);
        return ProductMapper.toProductResponseDTO(createProduct(product));
    }

    /**
     * Update an existing product from a request DTO.
     *
     * @param id product ID
     * @param productUpdateRequestDTO request data containing fields to update
     * @return updated ProductResponseDTO
     */
    public ProductResponseDTO updateProductResponse(long id,
                                                    ProductUpdateRequestDTO productUpdateRequestDTO){
        Product product = ProductMapper.toProduct(productUpdateRequestDTO);
        product.setId(id);
        return ProductMapper.toProductResponseDTO(updateProduct(product));
    }
}
