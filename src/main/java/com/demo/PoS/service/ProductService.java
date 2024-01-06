package com.demo.PoS.service;

import com.demo.PoS.dto.ProductDetails;
import com.demo.PoS.dto.ProductDto;
import com.demo.PoS.exceptions.NotFoundException;
import com.demo.PoS.model.entity.Discount;
import com.demo.PoS.model.entity.Product;
import com.demo.PoS.repository.DiscountRepository;
import com.demo.PoS.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final DiscountRepository discountRepository;

    public ProductService(ProductRepository productRepository, DiscountRepository discountRepository) {
        this.productRepository = productRepository;
        this.discountRepository = discountRepository;
    }

    public List<ProductDto> findAll() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(product ->
                ProductDto.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .stock(product.getStock())
                        .discountId(Optional.ofNullable(product.getDiscount()).map(Discount::getId).orElse(null))
                        .build()
        ).collect(Collectors.toList());
    }


    public ProductDto saveProduct(ProductDetails productDetails) {
        Product product = Product.builder()
                .name(productDetails.getName())
                .description(productDetails.getDescription())
                .price(productDetails.getPrice())
                .stock(productDetails.getStock())
                .build();

        productRepository.save(product);

        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .discountId(null)
                .build();
    }

    public ProductDto findById(UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + productId));
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .discountId(null)
                .build();
    }

    @Transactional
    public ProductDto updateProduct(UUID productId, ProductDetails productDetails) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + productId));

        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setStock(productDetails.getStock());

        Optional.ofNullable(productDetails.getDiscountId())
                .ifPresentOrElse(discountId -> {
                    Discount discount = discountRepository.findById(discountId)
                            .orElseThrow(() -> new NotFoundException("Discount not found with id: " + discountId));
                    product.setDiscount(discount);
                }, () -> product.setDiscount(null));

        productRepository.save(product);

        return ProductDto.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .discountId(Optional.ofNullable(product.getDiscount()).map(Discount::getId).orElse(null))
                .build();
    }


    public void deleteById(UUID productId) {
        if (!productRepository.existsById(productId)) {
            throw new NotFoundException("Product not found with id: " + productId);
        }
        productRepository.deleteById(productId);
    }

    @Transactional
    public ProductDto restockProduct(UUID productId, Integer newStock) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + productId));

        product.setStock(newStock);
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .discountId(null)
                .build();
    }
}
