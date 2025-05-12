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
		LocalDate arrivalDate = LocalDate.parse(availabilityDate, DateTimeFormatter.ofPattern("yyyyMMdd"));
		return findBookingsByDate(hotelId, roomType, arrivalDate);
	}

	public Integer findRoomsBookedForBothDates(String hotelId, String availabilityDate, String roomType) {
		LocalDate arrivalDate = LocalDate.parse(availabilityDate.substring(0, (availabilityDate.indexOf('-'))),
				DateTimeFormatter.ofPattern("yyyyMMdd"));
		LocalDate departureDate = LocalDate.parse(availabilityDate.substring((availabilityDate.indexOf('-') + 1)),
				DateTimeFormatter.ofPattern("yyyyMMdd"));
		return findBookingsByDates(hotelId, roomType, arrivalDate, departureDate);
	}
	
	public Integer findBookingsByDate(String hotelId, String roomType, LocalDate arrivalDate) {
		return bookingsService.findBookingsByDate(hotelId, roomType, arrivalDate);
	}

	public Integer findBookingsByDates(String hotelId, String roomType, LocalDate arrivalDate,
			LocalDate departureDate) {
		return bookingsService.findBookingsByDates(hotelId, roomType, arrivalDate, departureDate);
	}
	
	public LinkedList<Bookings> findAllDates(String hotelId, String roomType, LocalDate startDate, LocalDate endDate) {
		Bookings lastBookings = null;
		LinkedList<Bookings> newBookingsList = new LinkedList<Bookings>();

		// Find all Booking dates from database, also ADD dates in between, before &
		// after booking dates.
		List<Bookings> bookingsList = findAllBookingsByDates(hotelId, roomType, startDate, endDate);

		if (null != bookingsList && bookingsList.isEmpty()) {
			newBookingsList.add(new Bookings(-1, startDate, endDate));
		}

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

		return newBookingsList;
	}

	public List<Bookings> findAllBookingsByDates(String hotelId, String roomType, LocalDate startDate,
			LocalDate endDate) {
		return bookingsService.findAllBookingsByDates(hotelId, roomType, startDate, endDate);
	}
	
	public void checkAvailability(String hotelId, LinkedList<Bookings> newBookingsList, String roomType) {
		// Find Total Rooms available
		IAvailabilityReponse hotelsData = findHotelsData(hotelId, roomType);

		Integer totalRoomsBooked;
		// Find Total Rooms Booked
		for (int i = 0; i < newBookingsList.size(); i++) {
			totalRoomsBooked = findBookingsByDates(hotelId, roomType, newBookingsList.get(i).getArrival(),
					newBookingsList.get(i).getDeparture());

			newBookingsList.get(i).setSrno(hotelsData.getAvailability() - totalRoomsBooked);
		}
	}
	
	public String prepareDisplayData(LinkedList<Bookings> newBookingsList) {
		String displayData = null;
		StringBuffer strBuf = new StringBuffer();
		newBookingsList.stream()
				.forEach(b -> strBuf.append("(" + b.getArrival().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "-"
						+ b.getDeparture().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "," + b.getSrno() + "),"));

		displayData = strBuf.toString().substring(0, strBuf.length() - 1);
		System.out.println("Search Command :: " + displayData);
		System.out.println("----------");
		return displayData;
	}
}
