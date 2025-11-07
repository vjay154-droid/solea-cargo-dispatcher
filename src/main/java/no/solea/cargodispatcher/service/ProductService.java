package no.solea.cargodispatcher.service;

import no.solea.cargodispatcher.dto.ProductRequestDTO;
import no.solea.cargodispatcher.dto.ProductResponseDTO;
import no.solea.cargodispatcher.loader.DataLoader;
import no.solea.cargodispatcher.mapper.ProductMapper;
import no.solea.cargodispatcher.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProductService {
    private final DataLoader dataLoader;

    public ProductService(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    public List<Product> getProducts(){
        return dataLoader.getProducts();
    }

    public Product getProductById(long id){
       return dataLoader.getProducts().stream()
                .filter(product1 -> product1.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found"));
    }

    public Product createProduct(Product product){

        List<Product> products = dataLoader.getProducts();

        long newId = products.stream()
                .mapToLong(Product::getId)
                .max()
                .orElse(0) + 1;
        product.setId(newId);

        products.add(product);
        dataLoader.saveProductsToFile(products);

        return product;
    }

    public Product updateProduct(Product product){
        Product existingProduct = getProductById(product.getId());

        if (product.getName() != null)
            existingProduct.setName(product.getName());
        if (product.getSize() != null)
            existingProduct.setSize(product.getSize());

        return existingProduct;
    }

    public ProductResponseDTO getProductResponseById(long id){
        return ProductMapper.toProductResponseDTO(getProductById(id));
    }

    public List<ProductResponseDTO> getProductResponseList(){
        return ProductMapper.toProductResponseDTO(getProducts());
    }

    public ProductResponseDTO createProductResponse(ProductRequestDTO productRequestDTO){
        Product product = ProductMapper.toProduct(productRequestDTO);
        return ProductMapper.toProductResponseDTO(createProduct(product));
    }

    public ProductResponseDTO updateProductResponse(ProductRequestDTO productRequestDTO){
        Product product = ProductMapper.toProduct(productRequestDTO);
        return ProductMapper.toProductResponseDTO(updateProduct(product));
    }
}
