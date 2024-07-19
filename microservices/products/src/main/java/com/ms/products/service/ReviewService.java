package com.ms.products.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.products.config.infra.security.TokenService;
import com.ms.products.model.reviews.ReviewDTO;
import com.ms.products.model.reviews.ReviewModel;
import com.ms.products.repository.ReviewRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class ReviewService {
	@Autowired
	private ReviewRepository reviewRepository;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private ProductService productService;

	public ReviewDTO addReview(String productId, ReviewDTO reviewDTO, HttpServletRequest httpServletRequest) {
		var token = this.tokenService.recoverToken(httpServletRequest);
		var userInfo = this.tokenService.getTokenInformations(token);
		ReviewDTO newReview = new ReviewDTO(productId, userInfo.getClaim("userId").asString(),
				userInfo.getClaim("name").asString(), reviewDTO.comment(), reviewDTO.rating(),
				reviewDTO.createdAt());
		ReviewModel reviewModel = new ReviewModel(newReview);
		var addedReview = this.reviewRepository.insert(reviewModel);
		this.addReviewToProduct(productId, addedReview);
		
		return newReview;
	}
	private void addReviewToProduct (String productId, ReviewModel reviewModel) {
		var product = this.productService.getProductById(productId);
		product.getReviews().add(reviewModel.getId());
		product.setReviewsCount(product.getReviewsCount()+1);
		int reviewsCount = product.getReviewsCount();
		Float rating = product.getRating();
		rating =(Float) (rating+reviewModel.getRating())/reviewsCount;
		product.setRating(rating);
		this.productService.saveNewProduct(product);
		
		
	}
	

}
