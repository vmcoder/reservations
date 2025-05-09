package com.example.reservations.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.reservations.entity.Hotels;

@Repository
public interface HotelsRepository extends JpaRepository<Hotels, Long>{

}
