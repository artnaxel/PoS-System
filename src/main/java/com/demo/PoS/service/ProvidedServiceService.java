package com.demo.PoS.service;

import com.demo.PoS.dto.ProvidedServiceDetails;
import com.demo.PoS.dto.ProvidedServiceDto;
import com.demo.PoS.exceptions.NotFoundException;
import com.demo.PoS.model.entity.Discount;
import com.demo.PoS.model.entity.ProvidedService;
import com.demo.PoS.repository.DiscountRepository;
import com.demo.PoS.repository.ProvidedServiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
@Service
public class ProvidedServiceService {

    private final ProvidedServiceRepository providedServiceRepository;

    private final DiscountRepository discountRepository;

    public ProvidedServiceService(ProvidedServiceRepository providedServiceRepository,
                                  DiscountRepository discountRepository) {
        this.providedServiceRepository = providedServiceRepository;
        this.discountRepository = discountRepository;
    }

    public List<ProvidedServiceDto> getAllProvidedServices() {
        List<ProvidedService> providedServices = providedServiceRepository.findAll();

        return providedServices.stream().map(providedService ->
                ProvidedServiceDto.builder()
                        .id(providedService.getId())
                        .name(providedService.getName())
                        .description(providedService.getDescription())
                        .price(providedService.getPrice())
                        .discountId(Optional.ofNullable(providedService.getDiscount()).map(Discount::getId).orElse(null))
                        .build()
        ).collect(Collectors.toList());
    }

    @Transactional
    public ProvidedServiceDto createProvidedService(ProvidedServiceDetails providedServiceDetails) {
        ProvidedService providedService = ProvidedService.builder()
                .name(providedServiceDetails.getName())
                .description(providedServiceDetails.getDescription())
                .price(providedServiceDetails.getPrice())
                .build();

        providedServiceRepository.save(providedService);

        return ProvidedServiceDto.builder()
                .id(providedService.getId())
                .name(providedService.getName())
                .description(providedService.getDescription())
                .price(providedService.getPrice())
                .discountId(Optional.ofNullable(providedService.getDiscount()).map(Discount::getId).orElse(null))
                .build();
    }

    public ProvidedServiceDto findById(UUID providedServiceId) {
        ProvidedService providedService = providedServiceRepository.findById(providedServiceId)
                .orElseThrow(() -> new NotFoundException("Service not found with id: " + providedServiceId));

        return ProvidedServiceDto.builder()
                .id(providedService.getId())
                .name(providedService.getName())
                .description(providedService.getDescription())
                .price(providedService.getPrice())
                .discountId(Optional.ofNullable(providedService.getDiscount()).map(Discount::getId).orElse(null))
                .build();
    }

    @Transactional
    public ProvidedServiceDto updateProvidedService(UUID providedServiceId, ProvidedServiceDetails providedServiceDetails) {
        ProvidedService providedService = providedServiceRepository.findById(providedServiceId)
                .orElseThrow(() -> new NotFoundException("Service not found with id: " + providedServiceId));

        providedService.setName(providedServiceDetails.getName());
        providedService.setDescription(providedServiceDetails.getDescription());
        providedService.setPrice(providedServiceDetails.getPrice());

        Optional.ofNullable(providedServiceDetails.getDiscountId())
                .ifPresentOrElse(discountId -> {
                    Discount discount = discountRepository.findById(discountId)
                            .orElseThrow(() -> new NotFoundException("Discount not found with id: " + discountId));
                    providedService.setDiscount(discount);
                }, () -> providedService.setDiscount(null));

        providedServiceRepository.save(providedService);

        return ProvidedServiceDto.builder()
                .id(providedService.getId())
                .name(providedService.getName())
                .description(providedService.getDescription())
                .price(providedService.getPrice())
                .discountId(Optional.ofNullable(providedService.getDiscount()).map(Discount::getId).orElse(null))
                .build();
    }

    public void deleteById(UUID providedServiceId) {
        if(!providedServiceRepository.existsById(providedServiceId)) {
            throw new NotFoundException("Service not found with id: " + providedServiceId);
        }
        providedServiceRepository.deleteById(providedServiceId);
    }
}
