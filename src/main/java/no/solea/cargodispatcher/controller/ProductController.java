package no.solea.cargodispatcher.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import no.solea.cargodispatcher.dto.ProductRequestDTO;
import no.solea.cargodispatcher.dto.ProductResponseDTO;
import no.solea.cargodispatcher.dto.ProductUpdateRequestDTO;
import no.solea.cargodispatcher.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing products.
 * Provides endpoints to create, update, and fetch product information.
 */
@RestController
@RequestMapping("/products")
@Slf4j
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Retrieve all products.
     *
     * @return List of ProductResponseDTO wrapped in ResponseEntity with HTTP 200.
     */
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getProducts(){
        log.info("Get /products called");
        return ResponseEntity.ok(
                productService.getProductResponseList()
        );
    }

    /**
     * Retrieve a product by its ID.
     *
     * @param id The ID of the product to retrieve.
     * @return ProductResponseDTO wrapped in ResponseEntity with HTTP 200.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable long id){
        log.info("Get /products/{} called",id);
        return ResponseEntity.ok(
                productService.getProductResponseById(id)
        );
    }

    /**
     * Create a new product.
     *
     * @param productRequestDTO The product data to create.
     * @return ProductResponseDTO wrapped in ResponseEntity with HTTP 201.
     */
    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(
            @Valid @RequestBody ProductRequestDTO productRequestDTO){
        log.info("Post /products called with: {}",productRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        productService.createProductResponse(productRequestDTO)
                );
    }

    /**
     * Update an existing product.
     *
     * @param id The ID of the product to update.
     * @param productRequestDTO The product data to update. At least one field must be provided.
     * @return ProductResponseDTO wrapped in ResponseEntity with HTTP 200.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @PathVariable long id,
            @Valid @RequestBody ProductUpdateRequestDTO productRequestDTO){
        log.info("Patch /products/{} called with: {}",id,productRequestDTO);
        return ResponseEntity.ok(
                productService.updateProductResponse(id,productRequestDTO));
    }
}
