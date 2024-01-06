package com.demo.PoS.service;

import com.demo.PoS.dto.discount.DiscountRequest;
import com.demo.PoS.dto.discount.DiscountResponse;
import com.demo.PoS.exceptions.NotFoundException;
import com.demo.PoS.model.entity.Discount;
import com.demo.PoS.repository.DiscountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DiscountService {

    private final DiscountRepository discountRepository;

    public DiscountService(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    public DiscountResponse createDiscount(DiscountRequest discountRequest) {
        Discount discount = Discount.builder()
                .name(discountRequest.getName())
                .discountRate(discountRequest.getDiscountRate())
                .validFrom(discountRequest.getValidFrom())
                .validUntil(discountRequest.getValidUntil())
                .discountStatus(discountRequest.getDiscountStatus())
                .build();

        discountRepository.save(discount);

        return convertToDto(discount);
    }

    public DiscountResponse getDiscount(UUID id) {
       Discount discount =  discountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Discount not found"));

       return convertToDto(discount);
    }

    public List<DiscountResponse> getAllDiscounts() {
        List<Discount> discounts = discountRepository.findAll();
        return discounts.stream().map(this::convertToDto).collect(Collectors.toList());
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

        return convertToDto(discount);
    }

    private DiscountResponse convertToDto(Discount discount) {
        return DiscountResponse.builder()
                .id(discount.getId())
                .name(discount.getName())
                .discountRate(discount.getDiscountRate())
                .validFrom(discount.getValidFrom())
                .validUntil(discount.getValidUntil())
                .discountStatus(discount.getDiscountStatus())
                .build();
    }
    public void deleteDiscount(UUID id) {
        discountRepository.deleteById(id);
    }
}

