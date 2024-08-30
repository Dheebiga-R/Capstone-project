package com.spring.busbooking.service;

import com.spring.busbooking.dto.AuthenticationRequest;
import com.spring.busbooking.dto.AuthenticationResponse;
import com.spring.busbooking.dto.Register;
import com.spring.busbooking.model.User;

public interface AuthService {

	public User registerData(Register register);
	
	public User authenticateRequest(AuthenticationRequest request);
}
