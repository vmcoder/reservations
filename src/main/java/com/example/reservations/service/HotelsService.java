package com.example.reservations.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.reservations.dbresponse.IAvailabilityReponse;
import com.example.reservations.entity.Bookings;
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
		// Find Total Rooms available
		IAvailabilityReponse hotelsData = findHotelsData(hotelId, roomType);

		Integer totalRoomsBooked;
		// Find Total Rooms Booked
		if (availabilityDate.contains("-")) {
			totalRoomsBooked = findRoomsBookedForBothDates(hotelId, availabilityDate, roomType);

			// Rooms available - Rooms booked
			return (hotelsData.getAvailability() - totalRoomsBooked);
		}
		totalRoomsBooked = findRoomsBookedForSingleDate(hotelId, availabilityDate, roomType);

		// Rooms available - Rooms booked
		return (hotelsData.getAvailability() - totalRoomsBooked);
	}

	public IAvailabilityReponse findHotelsData(String hotelId, String roomType) {
		IAvailabilityReponse hotelsData = hotelsRepository.findHotelsByHotelIdAndRoomType(hotelId, roomType);
		System.out.println("In a Hotel given a RoomType, total Rooms available :- " + hotelsData.getHotelId() + ", "
				+ hotelsData.getRoomType() + ", " + hotelsData.getAvailability());
		return hotelsData;
	}

	public Integer findRoomsBookedForSingleDate(String hotelId, String availabilityDate, String roomType) {
		Integer totalRoomsBooked;
		LocalDate arrivalDate = LocalDate.parse(availabilityDate, DateTimeFormatter.ofPattern("yyyyMMdd"));
		totalRoomsBooked = bookingsService.findBookingsByDate(hotelId, roomType, arrivalDate);
		return totalRoomsBooked;
	}

	public Integer findRoomsBookedForBothDates(String hotelId, String availabilityDate, String roomType) {
		Integer totalRoomsBooked;
		LocalDate arrivalDate = LocalDate.parse(availabilityDate.substring(0, (availabilityDate.indexOf('-'))),
				DateTimeFormatter.ofPattern("yyyyMMdd"));
		LocalDate departureDate = LocalDate.parse(availabilityDate.substring((availabilityDate.indexOf('-') + 1)),
				DateTimeFormatter.ofPattern("yyyyMMdd"));
		totalRoomsBooked = bookingsService.findBookingsByDates(hotelId, roomType, arrivalDate, departureDate);
		return totalRoomsBooked;
	}
	
	public void findAllDates(String hotelId, String roomType, LocalDate startDate, LocalDate endDate) {
		List<Bookings> bookingsList = findAllBookingsByDates(hotelId, roomType, startDate, endDate);

		LinkedList<Bookings> newBookingsList = new LinkedList<Bookings>();
		Bookings lastBookings = null;
		for (int i = 0; i < bookingsList.size(); i++) {
			if (i == 0) {
				newBookingsList.add(new Bookings(-1, startDate, bookingsList.get(i).getArrival().minusDays(1)));
				newBookingsList.add(bookingsList.get(i));
			} else {
				newBookingsList.add(new Bookings(-1, lastBookings.getDeparture().plusDays(1),
						bookingsList.get(i).getArrival().minusDays(1)));
				newBookingsList.add(bookingsList.get(i));
			}
			if (i == (bookingsList.size() - 1)) {
				newBookingsList.add(new Bookings(-1, bookingsList.get(i).getDeparture().plusDays(1), endDate));
			}
			lastBookings = bookingsList.get(i);
		}
		newBookingsList.stream()
				.forEach(b -> System.out.println( "-> " +b.getSrno() + ", " + b.getArrival() + ", " + b.getDeparture()));
	}

	public List<Bookings> findAllBookingsByDates(String hotelId, String roomType, LocalDate startDate,
			LocalDate endDate) {
		return bookingsService.findAllBookingsByDates(hotelId, roomType, startDate, endDate);
	}
}
