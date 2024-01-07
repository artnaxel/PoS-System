package com.demo.PoS.mappers;

import com.demo.PoS.dto.receipt.ReceiptDto;
import com.demo.PoS.model.entity.Receipt;
import org.springframework.stereotype.Component;

@Component
public class ReceiptMapper {
    public ReceiptDto toDto(Receipt receipt) {
        return ReceiptDto.builder()
                .orderId(receipt.getOrder().getId())
                .receiptString(receipt.getText())
                .build();
    }
}
