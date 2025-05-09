package com.example.reservations.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.reservations.entity.Bookings;
import com.example.reservations.json.BookingsJson;
import com.example.reservations.repository.BookingsRepository;

@Service
public class BookingsService {

	@Autowired
	private BookingsRepository bookingsRepository;

	public void saveBookings(BookingsJson[] bookingsJson) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

		for (BookingsJson obj : bookingsJson) {
			Bookings bookings = new Bookings();
			bookings.setHotel_Id(obj.getHotelId());
			bookings.setRoom_rate(obj.getRoomRate());
			bookings.setRoom_type(obj.getRoomType());
			bookings.setArrival(LocalDate.parse(obj.getArrival(), formatter));
			bookings.setDeparture(LocalDate.parse(obj.getDeparture(), formatter));
			bookingsRepository.save(bookings);
		}
	}

}
