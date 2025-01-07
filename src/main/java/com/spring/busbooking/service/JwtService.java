package com.spring.busbooking.service;

import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;

import com.spring.busbooking.dto.AuthenticationRequest;
import com.spring.busbooking.model.User;

public interface JwtService {
	
	public String extractUsername(String token);

	public boolean isTokenValid(String jwtToken, UserDetails userDetails);

	public String generateToken(UserDetails user);
	
}
