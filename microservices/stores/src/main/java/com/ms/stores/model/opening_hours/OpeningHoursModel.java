package com.ms.stores.model.opening_hours;

import java.time.LocalTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "opening_hours")
public class OpeningHoursModel {
	@Id
	private String id;
	private String storeId;
	private DayOfWeek dayOfWeek;
	private String openTime;
	private String closeTime;
	
	
	public OpeningHoursModel(String id, String storeId, DayOfWeek dayOfWeek, String openTime, String closeTime) {
		this.id = id;
		this.storeId = storeId;
		this.dayOfWeek = dayOfWeek;
		this.openTime = openTime;
		this.closeTime = closeTime;
	}
	public OpeningHoursModel() {
	}
	public OpeningHoursModel(OpeningHoursDTO openingHoursDTO) {
		this.dayOfWeek = openingHoursDTO.dayOfWeek();
		this.openTime = openingHoursDTO.openTime();
		this.closeTime = openingHoursDTO.closeTime();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public DayOfWeek getDayOfWeek() {
		return dayOfWeek;
	}
	public void setDayOfWeek(DayOfWeek dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	public String getOpenTime() {
		return openTime;
	}
	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}
	public String getCloseTime() {
		return closeTime;
	}
	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
	}
	
	
}
