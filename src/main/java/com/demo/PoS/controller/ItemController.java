package com.demo.PoS.controller;

import com.demo.PoS.dto.ProductDetails;
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
public class ItemController {

    private final ProductService productService;

    public ItemController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.findAll();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product savedProduct = productService.saveProduct(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Item> getProductById(@PathVariable UUID productId) {
        Product product = productService.findById(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable UUID productId, @RequestBody ProductDetails productDetails) {
        Product updatedProduct = productService.updateProduct(productId, productDetails);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID productId) {
        productService.deleteById(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{productId}/restock")
    public ResponseEntity<Item> restockProduct(@PathVariable UUID productId, @RequestBody RestockRequest restockRequest) {
        Item updatedItem = productService.restockProduct(productId, restockRequest.getStock());
        return new ResponseEntity<>(updatedItem, HttpStatus.OK);
    }
}
