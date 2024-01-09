package com.demo.PoS.service;

import com.demo.PoS.dto.customer.CustomerLoyaltyResponse;
import com.demo.PoS.exceptions.NotFoundException;
import com.demo.PoS.mappers.CustomerMapper;
import com.demo.PoS.model.entity.Customer;
import com.demo.PoS.model.entity.LoyaltyProgram;
import com.demo.PoS.repository.CustomerRepository;
import com.demo.PoS.repository.LoyaltyProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final LoyaltyProgramRepository loyaltyProgramRepository;

    public List<CustomerLoyaltyResponse> getAllCustomers() {
        List<Customer> customerList = customerRepository.findAll();

        return customerList.stream()
                .map(CustomerMapper::toCustomerLoyaltyResponse)
                .collect(Collectors.toList());
    }
    public void assignLoyaltyProgram(UUID customerId, UUID loyaltyProgramId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Customer not found"));

        LoyaltyProgram loyaltyProgram = loyaltyProgramRepository.findById(loyaltyProgramId)
                .orElseThrow(() -> new NotFoundException("Loyalty program not found"));

        customer.setLoyaltyProgram(loyaltyProgram);

        customerRepository.save(customer);
    }

    public void removeLoyaltyProgram(UUID customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Customer not found"));

        customer.setLoyaltyProgram(null);

        customerRepository.save(customer);
    }


}
