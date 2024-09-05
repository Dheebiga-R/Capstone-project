package com.spring.busbooking.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.busbooking.dto.BookingDto;
import com.spring.busbooking.model.Booking;
import com.spring.busbooking.model.Bus;
import com.spring.busbooking.model.Passenger;
import com.spring.busbooking.repository.BookingRepository;
import com.spring.busbooking.repository.BusRepository;
import com.spring.busbooking.repository.PassengerRepository;
import com.spring.busbooking.validation.BusNotFoundException;
import com.spring.busbooking.validation.PassengerNotFoundException;

import jakarta.validation.Valid;

@Service
public class PassengerServiceImpl implements PassengerService{

	@Autowired
	private PassengerRepository passengerRepository;
	
	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private AdminService adminService;

	public PassengerServiceImpl(PassengerRepository passengerRepository,BookingRepository bookingRepository
			,AdminService adminService) {
		this.passengerRepository = passengerRepository;
		this.bookingRepository = bookingRepository;
		this.adminService = adminService;
	}

	@Override
	public void savePassenger(Passenger passenger) {
	    passengerRepository.save(passenger);
	}

	@Override
	public List<Passenger> getAllPassengers() {
		return passengerRepository.findAll();
	}

	@Override
	public Passenger findPassengerById(Integer id) {
		return passengerRepository.findById(id).get();
	}

	@Override
	public void update(Passenger passenger) {
	     passengerRepository.save(passenger);
	}

	//get the number of available seats
	@Override
	public Integer getAvailableSeats(BookingDto bookingDto) {
		Bus bus = adminService.findBusById(bookingDto.getBusId());
		int availableseats = bus.getAvailableSeat()-bookingDto.getSeatCount();
		bus.setAvailableSeat(availableseats);
		return  availableseats;
	}

	//save the booking details
	@Override
	public void booking(BookingDto bookingDto) throws BusNotFoundException, PassengerNotFoundException {
		Booking booking = new Booking();
		Bus bus = adminService.findBusById(bookingDto.getBusId());
		if(adminService.findBusById(bookingDto.getBusId())==null) {
			throw new BusNotFoundException("Bus nor present");
		}
		if(findPassengerById(bookingDto.getPassengerId())==null) {
			throw new PassengerNotFoundException("Passenger not found");
		}
		booking.setStatus("Booked successfully");
		booking.setBus(adminService.findBusById(bookingDto.getBusId()));
		booking.setPassenger(findPassengerById(bookingDto.getPassengerId()));
		booking.setSeatCount(bookingDto.getSeatCount());
		booking.setAvailableSeats(getAvailableSeats(bookingDto));
		booking.setFare(bookingDto.getSeatCount() * bus.getBusFare());
		bookingRepository.save(booking);
	}

	@Override
	public List<Booking> getAllBookings() {
		return bookingRepository.findAll();
	}

	@Override
	public Booking findBookingById(Integer id) {
		return bookingRepository.findById(id).get();
	}	
	
	
}
