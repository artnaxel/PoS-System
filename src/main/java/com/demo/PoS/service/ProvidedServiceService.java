package com.demo.PoS.service;

import com.demo.PoS.dto.providedService.ProvidedServiceRequest;
import com.demo.PoS.dto.providedService.ProvidedServiceResponse;
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

    public List<ProvidedServiceResponse> getAllProvidedServices() {
        List<ProvidedService> providedServices = providedServiceRepository.findAll();

        return providedServices.stream().map(providedService ->
                ProvidedServiceResponse.builder()
                        .id(providedService.getId())
                        .name(providedService.getName())
                        .description(providedService.getDescription())
                        .price(providedService.getPrice())
                        .discountId(Optional.ofNullable(providedService.getDiscount()).map(Discount::getId).orElse(null))
                        .build()
        ).collect(Collectors.toList());
    }

    @Transactional
    public ProvidedServiceResponse createProvidedService(ProvidedServiceRequest providedServiceRequest) {
        ProvidedService providedService = ProvidedService.builder()
                .name(providedServiceRequest.getName())
                .description(providedServiceRequest.getDescription())
                .price(providedServiceRequest.getPrice())
                .build();

        providedServiceRepository.save(providedService);

        return ProvidedServiceResponse.builder()
                .id(providedService.getId())
                .name(providedService.getName())
                .description(providedService.getDescription())
                .price(providedService.getPrice())
                .discountId(Optional.ofNullable(providedService.getDiscount()).map(Discount::getId).orElse(null))
                .build();
    }

    public ProvidedServiceResponse findById(UUID providedServiceId) {
        ProvidedService providedService = providedServiceRepository.findById(providedServiceId)
                .orElseThrow(() -> new NotFoundException("Service not found with id: " + providedServiceId));

        return ProvidedServiceResponse.builder()
                .id(providedService.getId())
                .name(providedService.getName())
                .description(providedService.getDescription())
                .price(providedService.getPrice())
                .discountId(Optional.ofNullable(providedService.getDiscount()).map(Discount::getId).orElse(null))
                .build();
    }

    @Transactional
    public ProvidedServiceResponse updateProvidedService(UUID providedServiceId, ProvidedServiceRequest providedServiceRequest) {
        ProvidedService providedService = providedServiceRepository.findById(providedServiceId)
                .orElseThrow(() -> new NotFoundException("Service not found with id: " + providedServiceId));

        providedService.setName(providedServiceRequest.getName());
        providedService.setDescription(providedServiceRequest.getDescription());
        providedService.setPrice(providedServiceRequest.getPrice());

        Optional.ofNullable(providedServiceRequest.getDiscountId())
                .ifPresentOrElse(discountId -> {
                    Discount discount = discountRepository.findById(discountId)
                            .orElseThrow(() -> new NotFoundException("Discount not found with id: " + discountId));
                    providedService.setDiscount(discount);
                }, () -> providedService.setDiscount(null));

        providedServiceRepository.save(providedService);

        return ProvidedServiceResponse.builder()
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
