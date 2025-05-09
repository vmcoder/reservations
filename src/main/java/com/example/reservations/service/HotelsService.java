package com.example.reservations.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.reservations.entity.Hotels;
import com.example.reservations.entity.RoomTypes;
import com.example.reservations.entity.Rooms;
import com.example.reservations.json.HotelsJson;
import com.example.reservations.json.RoomTypesJson;
import com.example.reservations.json.RoomsJson;
import com.example.reservations.repository.HotelsRepository;

@Service
public class HotelsService {

	@Autowired
	private HotelsRepository hotelsRepository;

	public void saveHotels(HotelsJson hotelsJson) {
		Hotels h = new Hotels();
		h.setId(hotelsJson.getId());
		h.setName(hotelsJson.getName());

		h.setRoomTypesList(new ArrayList<RoomTypes>());
		for (RoomTypesJson obj : hotelsJson.getRoomTypes()) {
			RoomTypes rt = new RoomTypes();
			rt.setCode(obj.getCode());
			rt.setDescription(obj.getDescription());
			rt.setAmenities(obj.getAmenities());
			rt.setFeatures(obj.getFeatures());
			h.getRoomTypesList().add(rt);
		}

		h.setRoomsList(new ArrayList<Rooms>());
		for (RoomsJson obj : hotelsJson.getRooms()) {
			Rooms r = new Rooms();
			r.setRoomType(obj.getRoomType());
			r.setRoomId(obj.getRoomId());
			h.getRoomsList().add(r);
		}

		hotelsRepository.save(h);
	}

}
