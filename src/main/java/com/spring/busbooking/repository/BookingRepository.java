package com.spring.busbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.busbooking.model.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer>{

}
