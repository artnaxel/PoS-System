package com.demo.PoS.service;

import com.demo.PoS.dto.DiscountDetails;
import com.demo.PoS.dto.DiscountDto;
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

    public DiscountDto createDiscount(DiscountDetails discountDetails) {
        Discount discount = Discount.builder()
                .name(discountDetails.getName())
                .discountRate(discountDetails.getDiscountRate())
                .validFrom(discountDetails.getValidFrom())
                .validUntil(discountDetails.getValidUntil())
                .discountStatus(discountDetails.getDiscountStatus())
                .build();

        discountRepository.save(discount);

        return convertToDto(discount);
    }

    public DiscountDto getDiscount(UUID id) {
       Discount discount =  discountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Discount not found"));

       return convertToDto(discount);
    }

    public List<DiscountDto> getAllDiscounts() {
        List<Discount> discounts = discountRepository.findAll();
        return discounts.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public DiscountDto updateDiscount(UUID id, DiscountDetails discountDetails) {
        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Discount not found"));

        discount.setName(discountDetails.getName());
        discount.setDiscountRate(discountDetails.getDiscountRate());
        discount.setValidFrom(discountDetails.getValidFrom());
        discount.setValidUntil(discountDetails.getValidUntil());
        discount.setDiscountStatus(discountDetails.getDiscountStatus());

        discountRepository.save(discount);

        return convertToDto(discount);
    }

    private DiscountDto convertToDto(Discount discount) {
        return DiscountDto.builder()
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

