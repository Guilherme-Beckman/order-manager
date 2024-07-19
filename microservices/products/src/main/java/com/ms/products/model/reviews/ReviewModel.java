package com.ms.products.model.reviews;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reviews")
public class ReviewModel {
	@Id
	private String id;
	private String productId;
	private String userId;
	private String userName;
	private String comment;
	private int rating;
	private LocalDateTime createdAt;
	
	
	public ReviewModel(ReviewDTO reviewDTO) {
		this.productId = reviewDTO.productId();
		this.userId = reviewDTO.userId();
		this.userName = reviewDTO.userName();
		this.comment = reviewDTO.comment();
		this.rating = reviewDTO.rating();
		this.createdAt = reviewDTO.createdAt();
	}
	public ReviewModel() {
	}

	public ReviewModel(String id, String productId, String userId, String userName, String comment, int rating,
			LocalDateTime createdAt) {
		this.id = id;
		this.productId = productId;
		this.userId = userId;
		this.userName = userName;
		this.comment = comment;
		this.rating = rating;
		this.createdAt = createdAt;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	
}
