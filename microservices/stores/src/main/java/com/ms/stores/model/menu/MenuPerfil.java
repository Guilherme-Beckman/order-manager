package com.ms.stores.model.menu;

import java.util.List;

import com.ms.products.model.product.ProductModel;

public record MenuPerfil(String id, String storeId, String name, List<ProductModel> productIds) {
}
