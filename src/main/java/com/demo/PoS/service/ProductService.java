package com.demo.PoS.service;

import com.demo.PoS.dto.product.ProductRequest;
import com.demo.PoS.dto.product.ProductResponse;
import com.demo.PoS.exceptions.NotFoundException;
import com.demo.PoS.mappers.ProductMapper;
import com.demo.PoS.model.entity.Discount;
import com.demo.PoS.model.entity.LoyaltyProgram;
import com.demo.PoS.model.entity.Product;
import com.demo.PoS.repository.DiscountRepository;
import com.demo.PoS.repository.LoyaltyProgramRepository;
import com.demo.PoS.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final DiscountRepository discountRepository;
    private final LoyaltyProgramRepository loyaltyProgramRepository;

    public List<ProductResponse> findAll() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(product ->
                ProductResponse.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .stock(product.getStock())
                        .discountId(Optional.ofNullable(product.getDiscount()).map(Discount::getId).orElse(null))
                        .loyaltyProgramId(Optional.ofNullable(product.getLoyaltyProgram()).map(LoyaltyProgram::getId).orElse(null))
                        .build()
        ).collect(Collectors.toList());
    }


    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .stock(productRequest.getStock())
                .build();

        productRepository.save(product);

        return ProductMapper.toProductResponse(product);
    }

    public ProductResponse findById(UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + productId));
        return ProductMapper.toProductResponse(product);
    }

    @Transactional
    public ProductResponse updateProduct(UUID productId, ProductRequest productRequest) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + productId));

        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setStock(productRequest.getStock());

        Optional.ofNullable(productRequest.getDiscountId())
                .ifPresentOrElse(discountId -> {
                    Discount discount = discountRepository.findById(discountId)
                            .orElseThrow(() -> new NotFoundException("Discount not found with id: " + discountId));
                    product.setDiscount(discount);
                }, () -> product.setDiscount(null));

        Optional.ofNullable(productRequest.getLoyaltyProgramId())
                .ifPresentOrElse(loyaltyProgramId -> {
                    LoyaltyProgram loyaltyProgram = loyaltyProgramRepository.findById(loyaltyProgramId)
                            .orElseThrow(() -> new NotFoundException("Loyalty Program not found with id: " + loyaltyProgramId));
                    product.setLoyaltyProgram(loyaltyProgram);
                }, () -> product.setDiscount(null));

        productRepository.save(product);

        return ProductMapper.toProductResponse(product);
    }


    public void deleteById(UUID productId) {
        if (!productRepository.existsById(productId)) {
            throw new NotFoundException("Product not found with id: " + productId);
        }
        productRepository.deleteById(productId);
    }

    @Transactional
    public ProductResponse restockProduct(UUID productId, Integer newStock) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + productId));

        product.setStock(newStock);
        return ProductMapper.toProductResponse(product);
    }
}
