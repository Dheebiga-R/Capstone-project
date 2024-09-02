package com.spring.busbooking.service;

import org.springframework.security.core.userdetails.UserDetails;

import com.spring.busbooking.model.User;

public interface JwtService {

	public String generateToken(UserDetails  user);
	
	public String extractUsername(String token);

	public boolean isTokenValid(String jwtToken, UserDetails userDetails);
	
}
