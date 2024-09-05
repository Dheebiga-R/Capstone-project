package com.spring.busbooking.service;

import java.util.List;

import com.spring.busbooking.dto.BookingDto;
import com.spring.busbooking.model.Booking;
import com.spring.busbooking.model.Passenger;
import com.spring.busbooking.validation.BusNotFoundException;
import com.spring.busbooking.validation.PassengerNotFoundException;

import jakarta.validation.Valid;

public interface PassengerService {

	void savePassenger(Passenger passenger);

	List<Passenger> getAllPassengers();

	Passenger findPassengerById(Integer id);

	void update(Passenger passenger);

	Integer getAvailableSeats(BookingDto bookingDto);

	void booking(BookingDto bookingDto) throws BusNotFoundException, PassengerNotFoundException;

	List<Booking> getAllBookings();

	Booking findBookingById(Integer id);
}
