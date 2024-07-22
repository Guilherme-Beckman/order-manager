package com.ms.orders.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.ms.orders.exceptions.rest.ProductNotFoundInCartException;
import com.ms.orders.infra.security.TokenService;
import com.ms.orders.model.cart.CartModel;
import com.ms.orders.rabbitMQ.producer.GetProductByIdProducer;
import com.ms.orders.repository.CartRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class CartService {
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private GetProductByIdProducer productByIdProducer;

	private CartModel getActiveCartByUserId(String id) {
		CartModel cartModel = this.cartRepository.findActiveCartsByUserId(id);
		return cartModel;
	}

	public CartModel addProductToCart(HttpServletRequest httpServletRequest, String productId) {
		var product = this.productByIdProducer.requestProductsByIdProducer(productId);
		var storeId = product.ownerid();
		var userInfo = this.getUserInfoByToken(httpServletRequest);
		String userId = userInfo.getClaim("userId").asString();

		var cart = this.getActiveCartByUserId(userId);
		if (cart == null) {
			CartModel newCart = new CartModel(userId);
			var map = new HashMap<String, Integer>();
			map.put(productId, 1);
			newCart.getStoreProductsId().put(storeId, map);
			newCart.setSubtotal(newCart.getSubtotal() + product.price());
			return this.cartRepository.insert(newCart);
		}

		var map = cart.getStoreProductsId();
		var storeProduct = map.computeIfAbsent(storeId, k -> new HashMap<>());
		storeProduct.put(productId, storeProduct.getOrDefault(productId, 0) + 1);
		System.out.println(product.price());
		cart.setSubtotal(cart.getSubtotal() + product.price());
		return this.cartRepository.save(cart);
	}

	private DecodedJWT getUserInfoByToken(HttpServletRequest request) {
		var token = this.tokenService.recoverToken(request);
		var userInfos = this.tokenService.getTokenInformations(token);
		return userInfos;
	}

	public CartModel removeProductFromCart(HttpServletRequest httpServletRequest, String productId) {
		var product = this.productByIdProducer.requestProductsByIdProducer(productId);
		var storeId = product.ownerid();
		var userInfo = this.getUserInfoByToken(httpServletRequest);
		String userId = userInfo.getClaim("userId").asString();

		var cart = this.getActiveCartByUserId(userId);
		var storeProducts = cart.getStoreProductsId().get(storeId);

		if (storeProducts == null) {
			throw new ProductNotFoundInCartException(productId);
		}

		var quantity = storeProducts.get(productId);
		if (quantity == null) {
			throw new ProductNotFoundInCartException(productId);
		}

		cart.setSubtotal(cart.getSubtotal() - product.price());

		if (quantity - 1 == 0) {
			storeProducts.remove(productId);
		} else {
			storeProducts.put(productId, quantity - 1);
		}

		if (storeProducts.isEmpty()) {
			cart.getStoreProductsId().remove(storeId);
		}

		return this.cartRepository.save(cart);
	}

}
