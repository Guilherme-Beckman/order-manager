package com.ms.products.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.products.config.infra.security.TokenService;
import com.ms.products.exceptions.ReviewNotFound;
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
		ReviewDTO newReview = new ReviewDTO(productId, userInfo.getClaim("name").asString(), reviewDTO.comment(),
				reviewDTO.rating(), reviewDTO.createdAt());
		ReviewModel reviewModel = new ReviewModel(newReview);
		reviewModel.setUserId(userInfo.getClaim("userId").asString());
		var addedReview = this.reviewRepository.insert(reviewModel);
		this.addReviewToProduct(productId, addedReview);

		return newReview;
	}

	private void addReviewToProduct(String productId, ReviewModel reviewModel) {
		var product = this.productService.getProductById(productId);
		product.getReviews().add(reviewModel.getId());
		product.setReviewsCount(product.getReviewsCount() + 1);
		if (product.getRating() == 0.0) {
			product.setRating(reviewModel.getRating());
			this.productService.saveNewProduct(product);
		}
		product.setRating((product.getRating() + reviewModel.getRating()) / 2);
		this.productService.saveNewProduct(product);

	}

	public ReviewDTO getReviewDTOById(String id) {
		var review = this.reviewRepository.findById(id).orElseThrow(ReviewNotFound::new);
		return new ReviewDTO(review.getProductId(), review.getUserName(), review.getComment(),
				review.getRating(), review.getCreatedAt());
	}

}
