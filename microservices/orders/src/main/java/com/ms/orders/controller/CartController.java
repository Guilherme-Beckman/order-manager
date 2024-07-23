package com.ms.orders.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms.orders.model.cart.CartModel;
import com.ms.orders.service.CartService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/cart")
public class CartController {
	@Autowired
	private CartService cartService;

	@PostMapping("/add/{productId}")
	public ResponseEntity<CartModel> addProductToCart(HttpServletRequest httpServletRequest,
			@PathVariable String productId) {
		var cart = this.cartService.addProductToCart(httpServletRequest, productId);
		return ResponseEntity.ok().body(cart);
	}

	@PutMapping("/remove/{productId}")
	public ResponseEntity<CartModel> removeProductToCart(HttpServletRequest httpServletRequest,
			@PathVariable String productId) {
		var cart = this.cartService.removeProductFromCart(httpServletRequest, productId);
		return ResponseEntity.ok().body(cart);
	}
}
