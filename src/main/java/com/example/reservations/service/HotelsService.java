package com.example.reservations.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.reservations.dbresponse.IAvailabilityReponse;
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
	
	@Autowired
	private BookingsService bookingsService;

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

	public Integer checkAvailability(String hotelId, String availabilityDate, String roomType) {
		//Find Total Rooms available
		IAvailabilityReponse hotelsData = hotelsRepository.findHotelsByHotelIdAndRoomType(hotelId, roomType);
		System.out.println("In a Hotel given a RoomType, total Rooms available :- " + hotelsData.getHotelId() + ", "
				+ hotelsData.getRoomType() + ", " + hotelsData.getAvailability());
		
		//Find Total Rooms Booked
		LocalDate arrivalDate = LocalDate.parse(availabilityDate, DateTimeFormatter.ofPattern("yyyyMMdd"));
		
		Integer totalRoomsBooked = bookingsService.findBookingsByDate(hotelId, roomType, arrivalDate);
		
		//Rooms available - Rooms booked
		if(null != hotelsData && null != hotelsData.getAvailability()) {
			return (hotelsData.getAvailability() - totalRoomsBooked);
		}
		
		return totalRoomsBooked;
	}

}
