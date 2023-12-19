package com.demo.PoS.dto;

import java.util.UUID;

public record OrderProductDto(
   UUID productId,
   Integer count
) {}
