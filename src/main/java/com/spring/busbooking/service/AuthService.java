package com.spring.busbooking.service;

import org.springframework.http.ResponseEntity;

import com.spring.busbooking.dto.AuthenticationRequest;
import com.spring.busbooking.dto.AuthenticationResponse;
import com.spring.busbooking.dto.Register;
import com.spring.busbooking.model.User;

public interface AuthService {

	public void registerData(Register register);

	public User findById(Integer id);
	
	public void updateUser(User user);

}
