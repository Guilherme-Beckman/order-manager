package com.ms.products.model.reviews;

import java.time.LocalDateTime;

public record ReviewDTO(String productId, String userName, String comment, Float rating, LocalDateTime createdAt) {
}
