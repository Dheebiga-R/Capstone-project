package com.spring.busbooking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.busbooking.model.Bus;
import com.spring.busbooking.model.User;
import com.spring.busbooking.repository.BusRepository;
import com.spring.busbooking.repository.UserRepository;

@Service
public class AdminServiceImpl implements AdminService{
	
	@Autowired
	private BusRepository busRepository;
	
	@Autowired
	private UserRepository userRepository;

	public AdminServiceImpl(BusRepository busRepository,UserRepository userRepository) {
		this.busRepository = busRepository;
		this.userRepository = userRepository;
	}

	@Override
	public void updateBus(Bus bus) {
		busRepository.save(bus);
	}

	@Override
	public List<Bus> getAllBuses() {
		return busRepository.findAll();
	}

	@Override
	public Bus findBusById(Integer id) {
		return busRepository.findById(id).get();
	}

	@Override
	public List<Bus> findByTerminalFromAndTerminalToAndDate(String from, String to, String date) {
		return busRepository.findByTerminalFromAndTerminalToAndDate(from,to,date);
	}

	@Override
	public User findByEmail(String name) {
		return userRepository.findByEmail(name).get();
	}

}
