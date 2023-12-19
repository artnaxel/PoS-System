package com.demo.PoS.dto;

import java.util.UUID;

public record ReceiptDto(
   UUID orderId,
   String receiptString
) {}
