package com.demo.PoS.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ReceiptDto(
   UUID orderId,
   String receiptString
) {}
