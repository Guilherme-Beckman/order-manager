package com.ms.orders.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.ms.orders.infra.security.TokenService;
import com.ms.orders.model.cart.CartModel;
import com.ms.orders.repository.CartRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class CartService {
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private TokenService tokenService;

	private CartModel getActiveCartByUserId(String id) {
		CartModel cartModel = this.cartRepository.findActiveCartsByUserId(id);
		return cartModel;
	}

	public CartModel addProductToCart(HttpServletRequest httpServletRequest, String productId) {
		var userInfo = this.getUserInfoByToken(httpServletRequest);
		String userId = userInfo.getClaim("userId").asString();
		var cart = this.getActiveCartByUserId(userId);
		if (cart == null) {
			CartModel newCart = new CartModel(userId);
			newCart.getProductsId().add(productId);
			return this.cartRepository.insert(newCart);
		}
		cart.getProductsId().add(productId);
		return this.cartRepository.save(cart);

	}

	private DecodedJWT getUserInfoByToken(HttpServletRequest request) {
		var token = this.tokenService.recoverToken(request);
		var userInfos = this.tokenService.getTokenInformations(token);
		return userInfos;
	}
}
