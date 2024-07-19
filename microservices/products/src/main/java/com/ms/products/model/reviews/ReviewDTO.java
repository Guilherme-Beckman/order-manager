package com.ms.products.model.reviews;

import java.time.LocalDateTime;

public record ReviewDTO(
    String productId,
    String userId,
    String userName,
    String comment,
    int rating,
    LocalDateTime createdAt
    ) {}
