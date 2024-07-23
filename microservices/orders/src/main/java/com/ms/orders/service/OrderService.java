package com.ms.orders.service;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.ms.orders.exceptions.rest.StoreDoesNotHaveAnyOrdersException;
import com.ms.orders.exceptions.rest.UserDontHaveActiveCartException;
import com.ms.orders.infra.security.TokenService;
import com.ms.orders.model.order.OrderModel;
import com.ms.orders.model.order.OrderPerfilForStores;
import com.ms.orders.model.product.ProductPerfil;
import com.ms.orders.rabbitMQ.producer.GetAddresByUserIdAdressIdProducer;
import com.ms.orders.rabbitMQ.producer.GetProductByIdProducer;
import com.ms.orders.repository.OrderRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class OrderService {
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private CartService cartService;
	@Autowired
	private GetProductByIdProducer productByIdProducer;
	@Autowired
	private GetAddresByUserIdAdressIdProducer addresByUserIdAdressIdProducer;

	public List<OrderModel> makeOrder(HttpServletRequest servletRequest, String addressId) {
		var userInfo = this.getUserInfoByToken(servletRequest);
		String userId = userInfo.getClaim("userId").asString();
		var cart = this.cartService.getActiveCartByUserId(userId);
		if (cart == null)
			throw new UserDontHaveActiveCartException(userId);
		List<OrderModel> orderModelList = new ArrayList<>();
		Map<String, Map<String, Integer>> stores = cart.getStoreProductsId();
		for (Map.Entry<String, Map<String, Integer>> storeEntry : stores.entrySet()) {
			OrderModel newOrder = new OrderModel();
			newOrder.setUserId(userId);
			newOrder.setStoreId(storeEntry.getKey());
			for (Map.Entry<String, Integer> productEntry : storeEntry.getValue().entrySet()) {
				var product = this.productByIdProducer.requestProductsByIdProducer(productEntry.getKey());
				var subtotal = product.price() * productEntry.getValue();
				newOrder.setSubtotal(subtotal);
				Map<String, Integer> mapOfProducts = new HashMap<>();
				mapOfProducts.put(productEntry.getKey(), productEntry.getValue());
				newOrder.getProductsId().add(mapOfProducts);
			}
			var addressDTO = this.addresByUserIdAdressIdProducer.requestAddressByUserIdAddressIdProducer(userId,
					addressId);
			newOrder.setAddressDTO(addressDTO);
			orderModelList.add(this.orderRepository.insert(newOrder));
		}
		this.cartService.disableCart(cart.getId());
		return orderModelList;
	}

	private DecodedJWT getUserInfoByToken(HttpServletRequest request) {
		var token = this.tokenService.recoverToken(request);
		var userInfos = this.tokenService.getTokenInformations(token);
		return userInfos;
	}

	public List<OrderPerfilForStores> getOrdersStore(HttpServletRequest servletRequest) {
		var storeInfo = this.getUserInfoByToken(servletRequest);
		String storeId = storeInfo.getClaim("userId").asString();
		List<OrderModel> orderModels = this.getOrdersByStoreId(storeId);
		List<OrderPerfilForStores> orderPerfils = new ArrayList<>();
		if(orderModels.isEmpty()) throw new StoreDoesNotHaveAnyOrdersException();
		orderModels.forEach(order -> {
			List<ProductPerfil> productsPerfilList = new ArrayList<>();
			var listProductsId = order.getProductsId();
			listProductsId.forEach(product -> {
				for (Map.Entry<String, Integer> productEntry : product.entrySet()) {
					var productModel = this.productByIdProducer.requestProductsByIdProducer(productEntry.getKey());
					ProductPerfil productPerfil = new ProductPerfil(productModel.id(), productModel.name(),
							productModel.description(), productEntry.getValue());
					productsPerfilList.add(productPerfil);
				}
			});
			OrderPerfilForStores perfilForStores = new OrderPerfilForStores(order.getId(), order.getUserId(),
					order.getOrderData(), order.getOrderStatus(), order.getSubtotal(), productsPerfilList, order.getAddressDTO());
			orderPerfils.add(perfilForStores);
		});
		return orderPerfils;
	}
	   public List<OrderModel> getOrdersByStoreId(String storeId) {
	        return orderRepository.findByStoreId(storeId);
	    }

}
