package com.demo.PoS.dto.order;

import java.util.UUID;

public record OrderProductDto(
   UUID productId,

   UUID discountId,

   UUID loyaltyProgramId,

   Integer count
) {}
