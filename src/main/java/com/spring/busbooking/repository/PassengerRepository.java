package com.spring.busbooking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.busbooking.model.Passenger;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Integer>{

	void save(Optional<Passenger> passenger);

}
