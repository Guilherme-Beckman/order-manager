package com.ms.products.model;

import java.net.URL;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products" )
public class ProductModel {
	@Id
	private String id;
	private String ownerid;
	private String menuId;
	private String name;
	private Integer price;
	private String descriptrion;
	private boolean avaliability;
	private Float rating;
	private Integer reviewsCount;
	private List<String> reviews;
	private String image;
	public ProductModel(String id, String ownerid, String menuId, String name, Integer price, String descriptrion,
			boolean avaliability, Float rating, Integer reviewsCount, List<String> reviews, String image) {
		this.id = id;
		this.ownerid = ownerid;
		this.menuId = menuId;
		this.name = name;
		this.price = price;
		this.descriptrion = descriptrion;
		this.avaliability = avaliability;
		this.rating = rating;
		this.reviewsCount = reviewsCount;
		this.reviews = reviews;
		this.image = image;
	}
	public ProductModel() {
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
	public String getDescriptrion() {
		return descriptrion;
	}
	public void setDescriptrion(String descriptrion) {
		this.descriptrion = descriptrion;
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
