package com.example.reservations.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "hotels")
public class Hotels {

	@Column(name = "srno")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long srno;

	@Column(name = "id")
	private String id;

	@Column(name = "name")
	private String name;

	@OneToMany(cascade = CascadeType.ALL)
	private List<RoomTypes> roomTypesList;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Rooms> roomsList;

	public long getSrno() {
		return srno;
	}

	public void setSrno(long srno) {
		this.srno = srno;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<RoomTypes> getRoomTypesList() {
		return roomTypesList;
	}

	public void setRoomTypesList(List<RoomTypes> roomTypesList) {
		this.roomTypesList = roomTypesList;
	}

	public List<Rooms> getRoomsList() {
		return roomsList;
	}

	public void setRoomsList(List<Rooms> roomsList) {
		this.roomsList = roomsList;
	}
}
