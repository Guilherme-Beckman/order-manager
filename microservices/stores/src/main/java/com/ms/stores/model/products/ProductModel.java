package com.ms.stores.model.products;

import java.util.List;

public class ProductModel {
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

	// Construtor
	public ProductModel(String id, String ownerid, String menuId, String name, Integer price, String description,
			boolean avaliability, Float rating, Integer reviewsCount, List<String> reviews) {
		this.id = id;
		this.ownerid = ownerid;
		this.menuId = menuId;
		this.name = name;
		this.price = price;
		this.description = description;
		this.avaliability = avaliability;
		this.rating = rating;
		this.reviewsCount = reviewsCount;
		this.reviews = reviews;
	}

	public ProductModel() {
	}

	// Getters e Setters
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

	@Override
	public String toString() {
		return "ProductModel{" + "id='" + id + '\'' + ", ownerid='" + ownerid + '\'' + ", menuId='" + menuId + '\''
				+ ", name='" + name + '\'' + ", price=" + price + ", description='" + description + '\''
				+ ", avaliability=" + avaliability + ", rating=" + rating + ", reviewsCount=" + reviewsCount
				+ ", reviews=" + reviews + '}';
	}
}
