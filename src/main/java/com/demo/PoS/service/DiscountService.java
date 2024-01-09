package com.demo.PoS.service;

import com.demo.PoS.dto.discount.DiscountRequest;
import com.demo.PoS.dto.discount.DiscountResponse;
import com.demo.PoS.exceptions.NotFoundException;
import com.demo.PoS.mappers.DiscountMapper;
import com.demo.PoS.model.entity.Discount;
import com.demo.PoS.model.entity.Product;
import com.demo.PoS.model.entity.ProvidedService;
import com.demo.PoS.repository.DiscountRepository;
import com.demo.PoS.repository.ProductRepository;
import com.demo.PoS.repository.ProvidedServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiscountService {

    private final DiscountRepository discountRepository;

    private final ProductRepository productRepository;

    private final ProvidedServiceRepository providedServiceRepository;

    public DiscountResponse createDiscount(DiscountRequest discountRequest) {
        Discount discount = Discount.builder()
                .name(discountRequest.getName())
                .discountRate(discountRequest.getDiscountRate())
                .validFrom(discountRequest.getValidFrom())
                .validUntil(discountRequest.getValidUntil())
                .discountStatus(discountRequest.getDiscountStatus())
                .build();

        discountRepository.save(discount);

        return DiscountMapper.toDiscountResponse(discount);
    }

    public DiscountResponse getDiscount(UUID id) {
       Discount discount =  discountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Discount not found"));

       return DiscountMapper.toDiscountResponse(discount);
    }

    public List<DiscountResponse> getAllDiscounts() {
        List<Discount> discounts = discountRepository.findAll();
        return discounts.stream()
                .map(DiscountMapper::toDiscountResponse)
                .collect(Collectors.toList());
    }

    public DiscountResponse updateDiscount(UUID id, DiscountRequest discountRequest) {
        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Discount not found"));

        discount.setName(discountRequest.getName());
        discount.setDiscountRate(discountRequest.getDiscountRate());
        discount.setValidFrom(discountRequest.getValidFrom());
        discount.setValidUntil(discountRequest.getValidUntil());
        discount.setDiscountStatus(discountRequest.getDiscountStatus());

        discountRepository.save(discount);

        return DiscountMapper.toDiscountResponse(discount);
    }

    public void deleteDiscount(UUID id) {
        List<Product> productsWithDiscount = productRepository.findByDiscount_Id(id);
        for (Product product : productsWithDiscount) {
            product.setDiscount(null);
            productRepository.save(product);
        }

        List<ProvidedService> providedServiceWithDiscount = providedServiceRepository.findByDiscount_Id(id);
        for (ProvidedService service : providedServiceWithDiscount) {
            service.setDiscount(null);
            providedServiceRepository.save(service);
        }

        discountRepository.deleteById(id);
    }
}

