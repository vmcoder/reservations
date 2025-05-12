package com.example.reservations.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.reservations.beans.ResponseBean;
import com.example.reservations.entity.Bookings;
import com.example.reservations.json.HotelsJson;
import com.example.reservations.service.HotelsService;

@RestController
public class HotelsController {

	private HotelsService hotelsService;

	public HotelsController() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Autowired
	public HotelsController(HotelsService hotelsService) {
		super();
		this.hotelsService = hotelsService;
	}

	@PostMapping(path = "/uploadHotels")
	public ResponseBean uploadHotels(@RequestBody HotelsJson hotelsJson) {
		ResponseBean bean = new ResponseBean();
		System.out.println("Saving Hotel with Hotel Id- " + hotelsJson.getId());

		try {
			hotelsService.saveHotels(hotelsJson);
			bean.setStatus("Success");
			bean.setMessage("ok");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Saving Hotel exception- " + e.getMessage());
			bean.setStatus("Error");
			bean.setMessage("failure");
		}
		return bean;
	}

	@GetMapping(path = "/checkAvailability")
	public Integer checkAvailability(@RequestParam("hotelId") String hotelId,
			@RequestParam("availabilityDate") String availabilityDate, @RequestParam("roomType") String roomType) {

		Integer availability = hotelsService.checkAvailability(hotelId, availabilityDate, roomType);

		return availability;
	}

	@GetMapping(path = "/search")
	public String search(@RequestParam("hotelId") String hotelId, @RequestParam("days") String days,
			@RequestParam("roomType") String roomType) {

		// validate days not null.
		if (null == days) {
			return "Blank Line";
		}

		List<Bookings> bookingsList = hotelsService.search(hotelId, days, roomType);

		if (null != bookingsList && bookingsList.isEmpty()) {
			return "Blank Line";
		}

		return hotelsService.prepareDisplayData(bookingsList);
	}
}
