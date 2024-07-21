package com.ms.stores.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms.stores.model.menu.MenuDTO;
import com.ms.stores.model.menu.MenuModel;
import com.ms.stores.model.menu.MenuPerfil;
import com.ms.stores.service.MenuService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/menu")
public class MenuController {
	@Autowired
	private MenuService menuService;
	@PostMapping("/create")
	public ResponseEntity<MenuModel> createMenu (@RequestBody MenuDTO menuDTO, HttpServletRequest servletRequest){
		var menuModel = this.menuService.createMenu(menuDTO, servletRequest);
		return ResponseEntity.ok().body(menuModel);
	}
	@PutMapping("productMenu/{menuId}/{productId}")
	public ResponseEntity<MenuModel> addProductMenu(@PathVariable String menuId, @PathVariable String productId){
		var menuModel = this.menuService.addProductMenu(menuId,productId);
		return ResponseEntity.ok().body(menuModel);
	}
	@GetMapping("/id/{menuId}")
	public ResponseEntity<MenuPerfil> getProductByMenuId(@PathVariable String menuId){
		MenuPerfil menuPerfil =this.menuService.getProductByMenuId(menuId);
		return ResponseEntity.ok().body(menuPerfil);
	}
	
}
