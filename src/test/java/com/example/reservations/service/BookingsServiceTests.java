package com.example.reservations.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.reservations.entity.Bookings;
import com.example.reservations.repository.BookingsRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookingsServiceTests {
	
	@Autowired
	private BookingsService bookingsService;
	
	@MockBean
	private BookingsRepository bookingsRepository;
	
	/*@Test
	public void test_nonEmpty_SaveBookings() {
		BookingsJson[] bookingsJson = new BookingsJson[1];
		BookingsJson json = new BookingsJson();
		json.setHotelId("H5");
		json.setRoomType("SGL");
		json.setRoomRate("Standard");
		json.setArrival(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
		json.setDeparture(LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("yyyyMMdd")));
		bookingsJson[0] = json;

		Bookings bookings = new Bookings();
		bookings.setHotel_Id("H5");
		bookings.setRoom_type("SGL");
		bookings.setRoom_rate("Standard");
		bookings.setArrival(LocalDate.now());
		bookings.setDeparture(LocalDate.now().plusDays(3));
		bookings.setSrno(123);
		
		Mockito.when(bookingsRepository.save(bookings)).thenReturn(bookings);

		Bookings[] bookingsArr = bookingsService.saveBookings(bookingsJson);

		assertNotNull(bookingsArr, "Savings did not return Entity");
		assertEquals(1, bookingsArr.length);
		assertNotNull(bookingsArr[0], "Entity is null");
		assertEquals("Standard", bookingsArr[0].getRoom_rate());
	}*/
	
	@Test
	public void test_nonEmpty_FindBookingsByDate() {
		String hotelId = new String("H5");
		String roomType = new String("SGL");
		LocalDate arrivalDate = LocalDate.now();

		AvailableResponse response = new AvailableResponse();
		response.setAvailability(1);
		Mockito.when(bookingsRepository.findBookingsByDate(hotelId, roomType, arrivalDate)).thenReturn(response);

		Integer availability = bookingsService.findBookingsByDate(hotelId, roomType, arrivalDate);

		assertEquals(1, availability);
	}
	
	@Test
	public void test_nonEmpty_FindBookingsByDates() {
		String hotelId = new String("H5"); 
		String roomType = new String("SGL"); 
		LocalDate arrivalDate = LocalDate.now();
		LocalDate departureDate = LocalDate.now().plusDays(60);
		
		AvailableResponse response = new AvailableResponse();
		response.setAvailability(2);
		Mockito.when(bookingsRepository.findBookingsByDates(hotelId, roomType, arrivalDate, departureDate)).thenReturn(response);
		
		Integer availability = bookingsService.findBookingsByDates(hotelId, roomType, arrivalDate, departureDate);
		
		assertEquals(2, availability);
	}
	
	@Test
	public void test_empty_FindAllBookingsByDates() {
		String hotelId = new String("H5"); 
		String roomType = new String("SGL"); 
		LocalDate startDate = LocalDate.now();
		LocalDate endDate = LocalDate.now().plusDays(10);
		
		List<Bookings> listBookings = new ArrayList<>();
		Mockito.when(bookingsRepository.findAllBookingsByDates(hotelId, roomType, startDate, endDate)).thenReturn(listBookings);
		
		List<Bookings> resultBookings = bookingsService.findAllBookingsByDates(hotelId, roomType, startDate, endDate);
		
		assertEquals(0, resultBookings.size());
	}
	
	@Test
	public void test_nonEmpty_FindAllBookingsByDates() {
		String hotelId = new String("H5");
		String roomType = new String("SGL");
		LocalDate startDate = LocalDate.now();
		LocalDate endDate = LocalDate.now().plusDays(30);

		List<Bookings> listBookings = new ArrayList<>();
		Bookings bookings = new Bookings();
		bookings.setSrno(1L);
		bookings.setHotel_Id("H5");
		bookings.setRoom_type("SGL");
		bookings.setRoom_rate("Standard");
		bookings.setArrival(LocalDate.now().plusDays(2));
		bookings.setDeparture(LocalDate.now().plusDays(4));
		listBookings.add(bookings);

		bookings = new Bookings();
		bookings.setSrno(2L);
		bookings.setHotel_Id("H5");
		bookings.setRoom_type("SGL");
		bookings.setRoom_rate("Standard");
		bookings.setArrival(LocalDate.now().plusDays(20));
		bookings.setDeparture(LocalDate.now().plusDays(22));
		listBookings.add(bookings);

		Mockito.when(bookingsRepository.findAllBookingsByDates(hotelId, roomType, startDate, endDate))
				.thenReturn(listBookings);

		List<Bookings> resultBookings = bookingsService.findAllBookingsByDates(hotelId, roomType, startDate, endDate);

		assertEquals(2, resultBookings.size());

		assertEquals("H5", resultBookings.get(0).getHotel_Id());
		assertEquals(LocalDate.now().plusDays(2), resultBookings.get(0).getArrival());
		
		assertEquals(LocalDate.now().plusDays(20), resultBookings.get(1).getArrival());
		assertEquals(LocalDate.now().plusDays(22), resultBookings.get(1).getDeparture());
	}

}
