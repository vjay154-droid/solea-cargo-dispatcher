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

@RestController
@RequestMapping("/products")
@Slf4j
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getProducts(){
        log.info("Get /products called");
        return ResponseEntity.ok(
                productService.getProductResponseList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable long id){
        log.info("Get /products/{} called",id);
        return ResponseEntity.ok(
                productService.getProductResponseById(id)
        );
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(
            @Valid @RequestBody ProductRequestDTO productRequestDTO){
        log.info("Post /products called with: {}",productRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        productService.createProductResponse(productRequestDTO)
                );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @PathVariable long id,
            @Valid @RequestBody ProductUpdateRequestDTO productRequestDTO){
        log.info("Patch /products/{} called with: {}",id,productRequestDTO);
        return ResponseEntity.ok(
                productService.updateProductResponse(id,productRequestDTO));
    }
}
