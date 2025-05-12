package com.example.reservations.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.reservations.beans.ResponseBean;
import com.example.reservations.json.BookingsJson;
import com.example.reservations.service.BookingsService;

@RestController
public class BookingsController {
	
	private BookingsService bookingsService;

	public BookingsController() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Autowired
	public BookingsController(BookingsService bookingsService) {
		super();
		this.bookingsService = bookingsService;
	}

	@PostMapping(path = "/uploadBookings")
	public ResponseBean uploadBookings(@RequestBody BookingsJson[] bookingsJson) {
		ResponseBean bean = new ResponseBean();
		System.out.println("Saving Hotel Bookings for Hotel Id-" + bookingsJson[0].getHotelId());

		try {
			bookingsService.saveBookings(bookingsJson);
			bean.setStatus("Success");
			bean.setMessage("ok");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Saving Hotel Bookings exception- " + e.getMessage());
			bean.setStatus("Error");
			bean.setMessage("failure");
		}
		return bean;
	}

}
