package com.ms.stores.model.store;

import java.util.List;

import com.ms.stores.model.address.AddressDTO;
import com.ms.stores.model.opening_hours.OpeningHoursDTO;
import com.ms.stores.model.products.ProductModel;

public record StorePerfil(String id, String name, AddressDTO addressDTO, String phone, List<OpeningHoursDTO> hoursDTOs,
		List<ProductModel> productModel

) {

}
