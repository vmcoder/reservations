package com.example.reservations.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.reservations.json.BookingsJson;
import com.example.reservations.service.BookingsService;

@RestController
public class BookingsController {

	@Autowired
	private BookingsService bookingsService;

	@PostMapping(path = "/uploadBookings")
	public void uploadBookings(@RequestBody BookingsJson[] bookingsJson) {
		System.out.println("Shri - Welcome to Hotel Bookings");
		System.out.println("Data 1- " + bookingsJson.length);
		System.out.println("Data 2- " + bookingsJson[0].getHotelId());
		System.out.println("Data 3- " + bookingsJson[0].getArrival());
		System.out.println("Data 4- " + bookingsJson[0].getDeparture());
		System.out.println("Data 5- " + bookingsJson[0].getRoomType());
		System.out.println("Data 6- " + bookingsJson[0].getRoomRate());

		bookingsService.saveBookings(bookingsJson);
	}

}
