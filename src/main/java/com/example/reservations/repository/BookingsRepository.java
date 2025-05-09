package com.example.reservations.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.reservations.entity.Bookings;

@Repository
public interface BookingsRepository extends JpaRepository<Bookings, Long>{

}
