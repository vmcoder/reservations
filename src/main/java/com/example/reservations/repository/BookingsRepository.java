package com.example.reservations.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.reservations.dbresponse.IAvailabilityReponse;
import com.example.reservations.entity.Bookings;

@Repository
public interface BookingsRepository extends JpaRepository<Bookings, Long> {

	@Query(nativeQuery = true, value = "select count(srno) as availability"
			+ " from bookings where hotel_id = :hotelId and room_type = :roomType" + " and arrival = :arrivalDate")
	IAvailabilityReponse findBookingsByDate(@Param("hotelId") String hotelId, @Param("roomType") String roomType,
			@Param("arrivalDate") LocalDate arrivalDate);
}
