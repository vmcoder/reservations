package com.example.reservations.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.reservations.repository.HotelsRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HotelsServiceTests {
	
	@Autowired
	private HotelsService hotelsService;
	
	//@MockBean
	//private BookingsService bookingsService;
	
	@MockBean
	private HotelsRepository hotelsRepository;
	
	@Test
	public void test_nonEmpty_singleDate_CheckAvailability() {
		String hotelId = new String("H5");
		String roomType = new String("SGL");
		String availabilityDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("yyyyMMdd"))
				.toString();

		AvailableResponse response = new AvailableResponse();
		response.setAvailability(3);
		Mockito.when(hotelsRepository.findHotelsByHotelIdAndRoomType(hotelId, roomType)).thenReturn(response);

		//Mockito.when(bookingsService.findBookingsByDate(hotelId, roomType, LocalDate.now().plusDays(3))).thenReturn(1);

		Integer availability = hotelsService.checkAvailability(hotelId, availabilityDate, roomType);

		assertEquals(3, availability);
	}
	
	@Test
	public void test_nonEmpty_bothDates_CheckAvailability() {
		String hotelId = new String("H5");
		String roomType = new String("SGL");
		StringBuffer strBuf = new StringBuffer();
		strBuf.append(LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("yyyyMMdd")).toString())
				.append("-");
		strBuf.append(LocalDate.now().plusDays(6).format(DateTimeFormatter.ofPattern("yyyyMMdd")).toString());
		String availabilityDate = strBuf.toString();

		AvailableResponse response = new AvailableResponse();
		response.setAvailability(6);
		Mockito.when(hotelsRepository.findHotelsByHotelIdAndRoomType(hotelId, roomType)).thenReturn(response);

		//Mockito.when(bookingsService.findBookingsByDate(hotelId, roomType, LocalDate.now().plusDays(3),
			//	LocalDate.now().plusDays(6))).thenReturn(1);

		Integer availability = hotelsService.checkAvailability(hotelId, availabilityDate, roomType);

		assertEquals(6, availability);
	}

}
