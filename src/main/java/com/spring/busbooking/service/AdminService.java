package com.spring.busbooking.service;

import java.util.List;

import com.spring.busbooking.model.Bus;
import com.spring.busbooking.model.User;

public interface AdminService {

	void updateBus(Bus bus2);

    List<Bus> getAllBuses();

	Bus findBusById(Integer id);

	List<Bus> findByTerminalFromAndTerminalToAndDate(String from, String to, String date);

	User findByEmail(String name);
    
}
