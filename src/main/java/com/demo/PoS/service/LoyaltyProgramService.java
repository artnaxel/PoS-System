package com.demo.PoS.service;

import com.demo.PoS.dto.loyaltyProgram.LoyaltyProgramRequest;
import com.demo.PoS.dto.loyaltyProgram.LoyaltyProgramResponse;
import com.demo.PoS.exceptions.NotFoundException;
import com.demo.PoS.mappers.LoyaltyProgramMapper;
import com.demo.PoS.model.entity.LoyaltyProgram;
import com.demo.PoS.repository.LoyaltyProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoyaltyProgramService {

    private final LoyaltyProgramRepository loyaltyProgramRepository;

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
                .orElseThrow(() -> new NotFoundException("Discount not found"));

        loyaltyProgram.setName(loyaltyProgramRequest.getName());
        loyaltyProgram.setDiscountRate(loyaltyProgramRequest.getDiscountRate());

        loyaltyProgramRepository.save(loyaltyProgram);

        return LoyaltyProgramMapper.toLoyaltyProgramResponse(loyaltyProgram);
    }

    public void deleteLoyaltyProgram(UUID id) { loyaltyProgramRepository.deleteById(id); }

}
