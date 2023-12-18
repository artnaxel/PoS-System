package com.demo.PoS.service;

import com.demo.PoS.dto.ProductDetails;
import com.demo.PoS.exceptions.NotFoundException;
import com.demo.PoS.model.entity.Product;
import com.demo.PoS.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public Product findById(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + productId));
    }

    @Transactional
    public Product updateProduct(UUID productId, ProductDetails productDetails) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + productId));

        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setStock(productDetails.getStock());

        return productRepository.save(product);
    }


    public void deleteById(UUID productId) {
        if (!productRepository.existsById(productId)) {
            throw new NotFoundException("Product not found with id: " + productId);
        }
        productRepository.deleteById(productId);
    }

    @Transactional
    public Product restockProduct(UUID productId, Integer newStock) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + productId));

        product.setStock(newStock);
        return productRepository.save(product);
    }
}
