package com.demo.PoS.service;

import com.demo.PoS.dto.loyaltyProgram.LoyaltyProgramRequest;
import com.demo.PoS.dto.loyaltyProgram.LoyaltyProgramResponse;
import com.demo.PoS.exceptions.NotFoundException;
import com.demo.PoS.mappers.LoyaltyProgramMapper;
import com.demo.PoS.model.entity.Customer;
import com.demo.PoS.model.entity.LoyaltyProgram;
import com.demo.PoS.model.entity.Product;
import com.demo.PoS.model.entity.ProvidedService;
import com.demo.PoS.model.relationship.OrderProduct;
import com.demo.PoS.repository.CustomerRepository;
import com.demo.PoS.repository.LoyaltyProgramRepository;
import com.demo.PoS.repository.ProductRepository;
import com.demo.PoS.repository.ProvidedServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoyaltyProgramService {

    private final LoyaltyProgramRepository loyaltyProgramRepository;
    private final CustomerRepository customerRepository;
    private final ProvidedServiceRepository providedServiceRepository;
    private final ProductRepository productRepository;


    public LoyaltyProgramResponse createLoyaltyProgram(LoyaltyProgramRequest loyaltyProgramRequest) {
        LoyaltyProgram loyaltyProgram = LoyaltyProgram.builder()
                .name(loyaltyProgramRequest.getName())
                .discountRate(loyaltyProgramRequest.getDiscountRate())
                .build();

        loyaltyProgramRepository.save(loyaltyProgram);

        return LoyaltyProgramMapper.toLoyaltyProgramResponse(loyaltyProgram);
    }

    public LoyaltyProgramResponse getLoyaltyProgram(UUID id) {
        LoyaltyProgram loyaltyProgram = loyaltyProgramRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Loyalty Program not found"));

        return LoyaltyProgramMapper.toLoyaltyProgramResponse(loyaltyProgram);
    }

    public List<LoyaltyProgramResponse> getAllLoyaltyPrograms() {
        List<LoyaltyProgram> loyaltyProgramList = loyaltyProgramRepository.findAll();

        return loyaltyProgramList.stream()
                .map(LoyaltyProgramMapper::toLoyaltyProgramResponse)
                .collect(Collectors.toList());
    }

    public LoyaltyProgramResponse updateLoyaltyProgram(UUID id, LoyaltyProgramRequest loyaltyProgramRequest) {
        LoyaltyProgram loyaltyProgram = loyaltyProgramRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Loyalty Program not found"));

        loyaltyProgram.setName(loyaltyProgramRequest.getName());
        loyaltyProgram.setDiscountRate(loyaltyProgramRequest.getDiscountRate());

        loyaltyProgramRepository.save(loyaltyProgram);

        return LoyaltyProgramMapper.toLoyaltyProgramResponse(loyaltyProgram);
    }

    public void deleteLoyaltyProgram(UUID loyaltyProgramId) {
        LoyaltyProgram loyaltyProgram = loyaltyProgramRepository.findById(loyaltyProgramId)
                .orElseThrow(() -> new NotFoundException("Loyalty Program not found"));

        // Disassociate the loyalty program from all customers/services/products
        List<Customer> customersWithLoyaltyProgram = customerRepository.findByLoyaltyProgram(loyaltyProgram);
        for (Customer customer : customersWithLoyaltyProgram) {
            customer.setLoyaltyProgram(null);
            customerRepository.save(customer);
        }

        List<Product> productsWithLoyaltyProgram = productRepository.findByLoyaltyProgram(loyaltyProgram);
        for (Product product : productsWithLoyaltyProgram) {
            product.setLoyaltyProgram(null);
            productRepository.save(product);
        }

        List<ProvidedService> providedServiceWithLoyaltyProgram = providedServiceRepository.findByLoyaltyProgram(loyaltyProgram);
        for (ProvidedService service : providedServiceWithLoyaltyProgram) {
            service.setLoyaltyProgram(null);
            providedServiceRepository.save(service);
        }

        loyaltyProgramRepository.delete(loyaltyProgram);
    }

    public Set<Pair<OrderProduct, LoyaltyProgram>> getLoyaltyProgramsAndProductsByOrder(UUID orderId) {
        return loyaltyProgramRepository.findLoyaltyProgramsWithOrderProductByOrder(orderId)
                .orElse(Collections.emptySet());
    }

}

