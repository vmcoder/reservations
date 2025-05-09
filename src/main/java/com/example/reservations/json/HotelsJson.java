package com.example.reservations.json;

import java.util.List;

public class HotelsJson {

	private String id;
	private String name;
	private List<RoomTypesJson> roomTypes;
	private List<RoomsJson> rooms;

	public HotelsJson() {
		super();
		// TODO Auto-generated constructor stub
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

	public List<RoomTypesJson> getRoomTypes() {
		return roomTypes;
	}

	public void setRoomTypes(List<RoomTypesJson> roomTypes) {
		this.roomTypes = roomTypes;
	}

	public List<RoomsJson> getRooms() {
		return rooms;
	}

	public void setRooms(List<RoomsJson> rooms) {
		this.rooms = rooms;
	}

}
