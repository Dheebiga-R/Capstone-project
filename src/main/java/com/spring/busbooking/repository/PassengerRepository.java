package com.spring.busbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.busbooking.model.Passenger;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Integer>{

}
