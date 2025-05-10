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
		
		LocalDate arrivalDate;
		LocalDate departureDate;
		Integer totalRoomsBooked;
		//Find Total Rooms Booked
		if(availabilityDate.contains("-")) {
			arrivalDate = LocalDate.parse(availabilityDate.substring(0, (availabilityDate.indexOf('-'))),
					DateTimeFormatter.ofPattern("yyyyMMdd"));
			
			departureDate = LocalDate.parse(availabilityDate.substring((availabilityDate.indexOf('-') + 1)),
					DateTimeFormatter.ofPattern("yyyyMMdd"));
			
			totalRoomsBooked = bookingsService.findBookingsByDates(hotelId, roomType, arrivalDate, departureDate);
			
			//Rooms available - Rooms booked
			return (hotelsData.getAvailability() - totalRoomsBooked);
		}
		
		arrivalDate = LocalDate.parse(availabilityDate, DateTimeFormatter.ofPattern("yyyyMMdd"));
		
		totalRoomsBooked = bookingsService.findBookingsByDate(hotelId, roomType, arrivalDate);
		
		//Rooms available - Rooms booked
		return (hotelsData.getAvailability() - totalRoomsBooked);
	}

}
