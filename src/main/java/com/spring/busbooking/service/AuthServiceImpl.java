package com.spring.busbooking.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.busbooking.dto.AuthenticationRequest;
import com.spring.busbooking.dto.AuthenticationResponse;
import com.spring.busbooking.dto.Register;
import com.spring.busbooking.model.User;
import com.spring.busbooking.repository.UserRepository;

import lombok.RequiredArgsConstructor;


@Service
public class AuthServiceImpl implements AuthService {
	@Autowired
	private UserRepository userRepository;

	private PasswordEncoder passwordEncoder;

	private AuthenticationManager authenticationManager;
	
	@Autowired
    private JwtService jwtService;
	
	public AuthServiceImpl(UserRepository userRepository,JwtService jwtService,AuthenticationManager authenticationManager,
			PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.jwtService = jwtService;
		this.authenticationManager=authenticationManager;
		this.passwordEncoder=passwordEncoder;
	}

	public AuthenticationResponse registerData(Register register) {
		User user =  User.builder()
				    .firstName(register.getFirstName())
				    .lastName(register.getLastName())
				    .email(register.getEmail())
				    .password(passwordEncoder.encode(register.getPassword()))
				    .role(register.getRole())
				    .build();
		userRepository.save(user);
		String jwtToken = jwtService.generateToken(user);
		return AuthenticationResponse.builder().accessToken(jwtToken).build();
	}

	public AuthenticationResponse authenticateRequest(AuthenticationRequest request) {
		
		authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                		request.getEmail(),
                		request.getPassword()
                )
        );
		User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
		String jwtToken = jwtService.generateToken(user);
		return AuthenticationResponse.builder().accessToken(jwtToken).build();
		
    }
		
}

