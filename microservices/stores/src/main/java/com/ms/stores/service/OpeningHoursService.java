package com.ms.stores.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.stores.model.opening_hours.OpeningHoursDTO;
import com.ms.stores.model.opening_hours.OpeningHoursModel;
import com.ms.stores.repository.OpeningHoursRepository;

@Service
public class OpeningHoursService {
	
	@Autowired
	private OpeningHoursRepository openingHoursRepository;
	
	public OpeningHoursModel addOpeningHours (String storeId, OpeningHoursDTO openingHoursDTO) {
		var newHours = new OpeningHoursModel(openingHoursDTO);
		newHours.setStoreId(storeId);
		return this.openingHoursRepository.save(newHours);
	}
}
