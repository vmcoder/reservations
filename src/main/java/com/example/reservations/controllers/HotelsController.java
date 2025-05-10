package com.example.reservations.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.reservations.json.HotelsJson;
import com.example.reservations.service.HotelsService;

@RestController
public class HotelsController {

	@Autowired
	private HotelsService hotelsService;

	@PostMapping(path = "/uploadHotels")
	public void uploadHotels(@RequestBody HotelsJson hotelsJson) {
		System.out.println("Shri - Welcome to Hotel Reservations");
		System.out.println("Data 1- " + hotelsJson);
		System.out.println("Data 2- " + hotelsJson.getId());
		System.out.println("Data 3- " + hotelsJson.getName());
		System.out.println("Data 4- " + hotelsJson.getRoomTypes());

		System.out.println("Data 41- " + hotelsJson.getRoomTypes().get(0).getCode());
		System.out.println("Data 42- " + hotelsJson.getRoomTypes().get(0).getDescription());
		System.out.println("Data 42- " + hotelsJson.getRoomTypes().get(0).getAmenities());

		System.out.println("Data 5- " + hotelsJson.getRooms());
		System.out.println("Data 51- " + hotelsJson.getRooms().get(0).getRoomType());
		System.out.println("Data 51- " + hotelsJson.getRooms().get(0).getRoomId());

		hotelsService.saveHotels(hotelsJson);
	}

	@GetMapping(path = "/checkAvailability")
	public Integer checkAvailability(@RequestParam("hotelId") String hotelId,
			@RequestParam("availabilityDate") String availabilityDate, @RequestParam("roomType") String roomType) {
		System.out.println("----------");
		System.out.println("checkAvailability for (hotelId, availabilityDate, roomType) - " + hotelId + ","
				+ availabilityDate + "," + roomType);
		
		Integer availability = hotelsService.checkAvailability(hotelId, availabilityDate, roomType);
		System.out.println("checkAvailability response :: " + availability);
		return availability;
	}

	@GetMapping(path = "/search")
	public String search(@RequestParam("hotelId") String hotelId, @RequestParam("days") String days,
			@RequestParam("roomType") String roomType) {
		System.out.println("search- " + hotelId);
		System.out.println("search- " + days);
		System.out.println("search- " + roomType);
		
		return "(20240901-20240210,2),(20240901-20240210,2)";
	}
}
