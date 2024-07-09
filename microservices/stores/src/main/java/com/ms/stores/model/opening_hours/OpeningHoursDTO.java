package com.ms.stores.model.opening_hours;

import java.time.LocalTime;

public record OpeningHoursDTO(
		  DayOfWeek dayOfWeek,
		  LocalTime openTime,
		  LocalTime closeTime 
		) {

}
