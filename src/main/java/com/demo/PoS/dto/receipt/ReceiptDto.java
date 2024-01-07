package com.demo.PoS.dto.receipt;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ReceiptDto(
   UUID orderId,
   String receiptString
) {}
