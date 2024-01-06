package com.demo.PoS.controller;

import com.demo.PoS.dto.ProductDetails;
import com.demo.PoS.dto.ProductDto;
import com.demo.PoS.dto.RestockRequest;
import com.demo.PoS.model.entity.Item;
import com.demo.PoS.model.entity.Product;
import com.demo.PoS.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> products = productService.findAll();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDetails product) {
        ProductDto savedProduct = productService.saveProduct(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable UUID productId) {
        ProductDto product = productService.findById(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable UUID productId, @RequestBody ProductDetails productDetails) {
        ProductDto updatedProduct = productService.updateProduct(productId, productDetails);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID productId) {
        productService.deleteById(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{productId}/restock")
    public ResponseEntity<ProductDto> restockProduct(@PathVariable UUID productId, @RequestBody RestockRequest restockRequest) {
        ProductDto updatedProduct = productService.restockProduct(productId, restockRequest.getStock());
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }
}
