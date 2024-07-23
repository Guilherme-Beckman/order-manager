package com.ms.products.model.product;

import java.util.List;

import com.ms.products.model.reviews.ReviewDTO;

public record ProductPerfil(String id, String ownerid, String name, Integer price, String menuId, String description,
		Float rating, Integer reviewsCount, List<ReviewDTO> reviews) {

}
