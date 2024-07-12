package com.ms.stores.model.opening_hours;


public record OpeningHoursDTO(
		  DayOfWeek dayOfWeek,
		  String openTime,
		  String closeTime 
		) {

}
