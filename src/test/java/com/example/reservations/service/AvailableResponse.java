package com.example.reservations.service;

import com.example.reservations.dbresponse.IAvailabilityReponse;

public class AvailableResponse implements IAvailabilityReponse {
	
	private Integer availability;

	@Override
	public String getHotelId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRoomType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getAvailability() {
		// TODO Auto-generated method stub
		return availability;
	}
	
	public void setAvailability(Integer arg) {
		availability = arg;
	}

}
