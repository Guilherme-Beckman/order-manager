package com.ms.products.model;
public record ProductDTO(
		String ownerid, 
		String menuId, 
		String name, 
		Integer price, 
		String description,
		boolean avaliability, 
		String image

) {

}
