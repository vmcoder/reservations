package com.example.reservations.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.reservations.dbresponse.IAvailabilityReponse;
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

	public Integer findBookingsByDate(String hotelId, String roomType, LocalDate arrivalDate) {
		IAvailabilityReponse bookingsResponseForADate = bookingsRepository.findBookingsByDate(hotelId, roomType,
				arrivalDate);

		System.out.println("Bookings on a Date, in a Hotel given a RoomType, total Rooms booked :- " + arrivalDate
				+ ", " + hotelId + ", " + roomType + ", " + bookingsResponseForADate.getAvailability());

		return bookingsResponseForADate.getAvailability();
	}

	public Integer findBookingsByDates(String hotelId, String roomType, LocalDate arrivalDate,
			LocalDate departureDate) {
		IAvailabilityReponse bookingsResponseForDates = bookingsRepository.findBookingsByDates(hotelId, roomType,
				arrivalDate, departureDate);

		System.out.println("Bookings on Dates, in a Hotel given a RoomType, total Rooms booked :- " + arrivalDate
				+ ", " + departureDate + ", " + hotelId + ", " + roomType + ", "
				+ bookingsResponseForDates.getAvailability());

		return bookingsResponseForDates.getAvailability();
	}
	
	public List<Bookings> findAllBookingsByDates(String hotelId, String roomType, LocalDate startDate,
			LocalDate endDate) {
		List<Bookings> bookingsList = bookingsRepository.findAllBookingsByDates(hotelId, roomType, startDate, endDate);
		bookingsList.stream().forEach(b -> System.out.println(b.getSrno() + ", " + b.getArrival() + ", " + b.getDeparture()));

		return bookingsList;
	}

}
