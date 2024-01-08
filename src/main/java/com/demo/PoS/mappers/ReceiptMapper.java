package com.demo.PoS.mappers;

import com.demo.PoS.dto.ReceiptDto;
import com.demo.PoS.model.entity.Receipt;

public class ReceiptMapper {
    public static ReceiptDto toDto(Receipt receipt) {
        return ReceiptDto.builder()
                .orderId(receipt.getOrder().getId())
                .receiptString(receipt.getText())
                .build();
    }
}
