package com.ms.products.model.product;

import java.util.List;

public class ProductModelDTO {
	private String ownerid;
	private String menuId;
	private String name;
	private Integer price;
	private String description;
	private boolean availability;
	private Float rating;
	private Integer reviewsCount;
	private List<String> reviews;

	public ProductModelDTO() {
	}

	public ProductModelDTO(String ownerId, String menuId, String name, Integer price, String description,
			boolean availability, Float rating, Integer reviewsCount, List<String> reviews, String id) {
		this.ownerid = ownerId;
		this.menuId = menuId;
		this.name = name;
		this.price = price;
		this.description = description;
		this.availability = availability;
		this.rating = rating;
		this.reviewsCount = reviewsCount;
		this.reviews = reviews;
		this.id = id;
	}

	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOwnerId() {
		return ownerid;
	}

	public void setOwnerId(String ownerId) {
		this.ownerid = ownerId;
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

	public boolean isAvailability() {
		return availability;
	}

	public void setAvailability(boolean availability) {
		this.availability = availability;
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

}
