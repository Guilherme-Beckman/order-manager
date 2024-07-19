package com.ms.products.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms.products.model.reviews.ReviewDTO;
import com.ms.products.service.ReviewService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/review")
public class ReviewController {

	@Autowired
	private ReviewService reviewService;

	@PostMapping("/{productId}")
	public ResponseEntity<ReviewDTO> addNewReview(@PathVariable String productId, @RequestBody ReviewDTO reviewDTO,
			HttpServletRequest httpServletRequest) {
		ReviewDTO savedReview = reviewService.addReview(productId, reviewDTO, httpServletRequest);
		return ResponseEntity.ok(savedReview);
	}
}