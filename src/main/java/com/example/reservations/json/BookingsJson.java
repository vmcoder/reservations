package com.example.reservations.json;

public class BookingsJson {

	private String hotelId;
	private String arrival;
	private String departure;
	private String roomType;
	private String roomRate;

	public BookingsJson() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getHotelId() {
		return hotelId;
	}

	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}

	public String getArrival() {
		return arrival;
	}

	public void setArrival(String arrival) {
		this.arrival = arrival;
	}

	public String getDeparture() {
		return departure;
	}

	public void setDeparture(String departure) {
		this.departure = departure;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public String getRoomRate() {
		return roomRate;
	}

	public void setRoomRate(String roomRate) {
		this.roomRate = roomRate;
	}

}
