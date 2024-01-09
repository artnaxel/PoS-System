package com.demo.PoS.dto.order;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderProductDto(
   UUID productId,

   UUID discountId,

   Integer count
) {}
