package com.spring.busbooking.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.busbooking.dto.AuthenticationRequest;
import com.spring.busbooking.dto.AuthenticationResponse;
import com.spring.busbooking.dto.Register;
import com.spring.busbooking.model.Role;
import com.spring.busbooking.model.User;
import com.spring.busbooking.repository.UserRepository;

import jakarta.annotation.PostConstruct;
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
	
//register the user information and encode the password
	public void registerData(Register register) {
		User user =  User.builder()
				    .firstName(register.getFirstName())
				    .lastName(register.getLastName())
				    .email(register.getEmail())
				    .password(passwordEncoder.encode(register.getPassword()))
				    .role(Role.USER)
				    .build();
		userRepository.save(user);
	}


	@Override
	public User findById(Integer id) {
		return userRepository.findById(id).get();
	}


	@Override
	public void updateUser(User user) {
		userRepository.save(user);
	}

	
	
	/*
	private static List<User> list = new ArrayList<>();
	
	static {
		list.add(new User(1,"admin","admin","ROLE_ADMIN",true));
		list.add(new User(2,"swapnil","1234","ROLE_NORMAL",true));
	}
	
	public User getByUsername(String userName)
	{
		User user = null;
	
		user = list.stream().filter(e ->e.getUsername().equals(userName)).findFirst().get();
	// user = 	new User(1,"admin","admin","ROLE_ADMIN",true);
		return user;
	}*/
		
}

