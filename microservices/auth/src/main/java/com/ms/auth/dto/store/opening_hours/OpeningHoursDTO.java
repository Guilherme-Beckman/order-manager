package com.ms.auth.dto.store.opening_hours;

import java.time.LocalTime;

public record OpeningHoursDTO(DayOfWeek dayOfWeek, LocalTime openTime, LocalTime closeTime) {

}
