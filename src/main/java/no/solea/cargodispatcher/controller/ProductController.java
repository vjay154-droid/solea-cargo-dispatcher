package no.solea.cargodispatcher.controller;

import no.solea.cargodispatcher.dto.ProductRequestDTO;
import no.solea.cargodispatcher.dto.ProductResponseDTO;
import no.solea.cargodispatcher.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getProducts(){
        return ResponseEntity.ok(
                productService.getProductResponseList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable long id){
        return ResponseEntity.ok(
                productService.getProductResponseById(id)
        );
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductRequestDTO productRequestDTO){

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        productService.createProductResponse(productRequestDTO)
                );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable long id,
                                                            @RequestBody ProductRequestDTO productRequestDTO){
        return ResponseEntity.ok(
                productService.updateProductResponse(id,productRequestDTO));
    }
}
