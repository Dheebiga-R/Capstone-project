package com.spring.busbooking.service;

import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
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
 
	@Autowired
	private PasswordEncoder passwordEncoder;
	
    @Autowired
	private AuthenticationManager authenticationManager;
    
    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,AuthenticationManager authenticationManager) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager=authenticationManager;
	}

	public User registerData(Register register) {
		User user =  User.builder()
				    .firstName(register.getFirstName())
				    .lastName(register.getLastName())
				    .email(register.getEmail())
				    .password(passwordEncoder.encode(register.getPassword()))
				    .role(register.getRole())
				    .build();
		return userRepository.save(user);
	}

	public User authenticateRequest(AuthenticationRequest request) {
		
		authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                		request.getEmail(),
                		request.getPassword()
                )
        );
		User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new IllegalArgumentException("User not found")); 
		return user;
    }
		
}

