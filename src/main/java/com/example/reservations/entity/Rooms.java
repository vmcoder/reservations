package com.example.reservations.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "rooms")
public class Rooms {

	@Column(name = "srno")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long srno;

	@Column(name = "room_type")
	private String roomType;

	@Column(name = "room_id")
	private int roomId;

	@ManyToOne
	private Hotels hotel;

	public Rooms() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getSrno() {
		return srno;
	}

	public void setSrno(long srno) {
		this.srno = srno;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public Hotels getHotel() {
		return hotel;
	}

	public void setHotel(Hotels hotel) {
		this.hotel = hotel;
	}

}
