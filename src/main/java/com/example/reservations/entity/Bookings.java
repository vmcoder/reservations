package com.example.reservations.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Bookings {

	@Column(name = "srno")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long srno;

	@Column(name = "hotel_Id")
	private String hotel_Id;

	@Column(name = "arrival")
	private LocalDate arrival;

	@Column(name = "departure")
	private LocalDate departure;

	@Column(name = "room_type")
	private String room_type;

	@Column(name = "room_rate")
	private String room_rate;

	public Bookings() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getSrno() {
		return srno;
	}

	public void setSrno(long srno) {
		this.srno = srno;
	}

	public String getHotel_Id() {
		return hotel_Id;
	}

	public void setHotel_Id(String hotel_Id) {
		this.hotel_Id = hotel_Id;
	}

	public LocalDate getArrival() {
		return arrival;
	}

	public void setArrival(LocalDate arrival) {
		this.arrival = arrival;
	}

	public LocalDate getDeparture() {
		return departure;
	}

	public void setDeparture(LocalDate departure) {
		this.departure = departure;
	}

	public String getRoom_type() {
		return room_type;
	}

	public void setRoom_type(String room_type) {
		this.room_type = room_type;
	}

	public String getRoom_rate() {
		return room_rate;
	}

	public void setRoom_rate(String room_rate) {
		this.room_rate = room_rate;
	}
}
