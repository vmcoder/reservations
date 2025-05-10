package com.example.reservations.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.reservations.dbresponse.IAvailabilityReponse;
import com.example.reservations.entity.Hotels;

@Repository
public interface HotelsRepository extends JpaRepository<Hotels, Long> {

	@Query(nativeQuery = true, 
			value = "select h.id as hotelId, rt.code as roomType, count(r.room_id) as availability from"
			+ " hotels h join hotels_room_types_list hr on h.srno = hr.hotels_srno and h.id = :hotelId"
			+ " join room_types rt on rt.srno = hr.room_types_list_srno and rt.code = :roomType"
			+ " join rooms r on r.room_type = rt.code " 
			+ " group by h.id, rt.code")
	IAvailabilityReponse findHotelsByHotelIdAndRoomType(@Param("hotelId") String hotelId, @Param("roomType") String roomType);
}
