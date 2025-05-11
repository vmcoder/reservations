package com.example.reservations.repository;

import java.time.LocalDate;
import java.util.List;

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
	
	@Query(nativeQuery = true, value = "select count(srno) as availability"
			+ " from bookings where hotel_id = :hotelId and room_type = :roomType"
			+ " and arrival = :arrivalDate and departure = :departureDate")
	IAvailabilityReponse findBookingsByDates(@Param("hotelId") String hotelId, @Param("roomType") String roomType,
			@Param("arrivalDate") LocalDate arrivalDate, @Param("departureDate") LocalDate departureDate);
	
	@Query(nativeQuery = true, value = "select *"
			+ " from bookings where hotel_id = :hotelId and room_type = :roomType"
			+ " and arrival >= :startDate and departure <= :endDate order by arrival asc")
	List<Bookings> findAllBookingsByDates(@Param("hotelId") String hotelId, @Param("roomType") String roomType,
			@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
