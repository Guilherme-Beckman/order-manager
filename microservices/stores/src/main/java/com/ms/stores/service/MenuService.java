package com.ms.stores.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.stores.exceptions.rest.MenuNotFoundException;
import com.ms.stores.exceptions.rest.ProductIsAlreadyOnTheMenuException;
import com.ms.stores.model.menu.MenuDTO;
import com.ms.stores.model.menu.MenuModel;
import com.ms.stores.rabbitMQ.producer.AddProductMenuProducer;
import com.ms.stores.rabbitMQ.producer.MenuProductDTO;
import com.ms.stores.repository.MenuRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class MenuService {
	@Autowired
	private MenuRepository menuRepository;
	@Autowired
	private StoreService storeService;
	@Autowired
	private AddProductMenuProducer productMenuProducer;
	public MenuModel createMenu(MenuDTO menuDTO, HttpServletRequest servletRequest) {
		var store = this.storeService.findStoreByToken(servletRequest);
		var menuModel = new MenuModel(menuDTO);
		menuModel.setStoreId(store.getId());
		return this.menuRepository.insert(menuModel);
	}

	public MenuModel getMenuId(String id) {
		return this.menuRepository.findById(id).orElseThrow(MenuNotFoundException::new);
	}

	public MenuModel addProductMenu(String menuId, String productId) {
		MenuProductDTO menuProductDTO = new MenuProductDTO(menuId, productId);
		this.productMenuProducer.addProductMenu(menuProductDTO);
		var menuModel = getMenuId(menuId);
		if (menuModel.getProductIds().contains(productId))
			throw new ProductIsAlreadyOnTheMenuException();
		menuModel.getProductIds().add(productId);
		return this.menuRepository.save(menuModel);
	}

}
