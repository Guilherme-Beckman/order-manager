package com.ms.products.model.product;
public record ProductDTO(
		String ownerid,
		String name, 
		Integer price, 
		String description,
		boolean avaliability
) {

}
