package com.demo.PoS.controller;

import com.demo.PoS.dto.product.ProductRequest;
import com.demo.PoS.dto.product.ProductResponse;
import com.demo.PoS.dto.product.RestockRequest;
import com.demo.PoS.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<ProductResponse> products = productService.findAll();
        return ResponseEntity.ok(products);
    }

    @PostMapping
    public ResponseEntity<ProductResponse> addProduct(@RequestBody @Valid ProductRequest product) {
        ProductResponse savedProduct = productService.saveProduct(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable UUID productId) {
        ProductResponse product = productService.findById(productId);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable UUID productId, @RequestBody @Valid ProductRequest productRequest) {
        ProductResponse updatedProduct = productService.updateProduct(productId, productRequest);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID productId) {
        productService.deleteById(productId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{productId}/restock")
    public ResponseEntity<ProductResponse> restockProduct(@PathVariable UUID productId, @RequestBody @Valid RestockRequest restockRequest) {
        ProductResponse updatedProduct = productService.restockProduct(productId, restockRequest.getStock());
        return ResponseEntity.ok(updatedProduct);
    }
}
