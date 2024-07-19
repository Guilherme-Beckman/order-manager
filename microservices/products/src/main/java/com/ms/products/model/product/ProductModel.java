package com.ms.products.model.product;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
public class ProductModel {
	@Id
	private String id;
	private String ownerid;
	private String menuId;
	private String name;
	private Integer price;
	private String description;
	private boolean avaliability;
	private Float rating;
	private Integer reviewsCount;
	private List<String> reviews;
	private String image;

	public ProductModel(String id, String ownerid, String menuId, String name, Integer price, String description,
			boolean avaliability, String image) {
		this.id = id;
		this.ownerid = ownerid;
		this.menuId = menuId;
		this.name = name;
		this.price = price;
		this.description = description;
		this.avaliability = avaliability;
		this.rating = 0F;
		this.reviewsCount = 0;
		this.reviews = new ArrayList<>();
		this.image = image;
	}
	

	public ProductModel() {
		this.reviews = new ArrayList<>();
	}
	
	public ProductModel(ProductDTO productDTO) {
		this.ownerid = productDTO.ownerid();
		this.name = productDTO.name();
		this.price = productDTO.price();
		this.description = productDTO.description();
		this.avaliability = productDTO.avaliability();
		this.rating = 0F;
		this.reviewsCount = 0;
		this.reviews = new ArrayList<>();
		this.image = productDTO.image();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOwnerid() {
		return ownerid;
	}

	public void setOwnerid(String ownerid) {
		this.ownerid = ownerid;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isAvaliability() {
		return avaliability;
	}

	public void setAvaliability(boolean avaliability) {
		this.avaliability = avaliability;
	}

	public Float getRating() {
		return rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}

	public Integer getReviewsCount() {
		return reviewsCount;
	}

	public void setReviewsCount(Integer reviewsCount) {
		this.reviewsCount = reviewsCount;
	}

	public List<String> getReviews() {
		return reviews;
	}

	public void setReviews(List<String> reviews) {
		this.reviews = reviews;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
