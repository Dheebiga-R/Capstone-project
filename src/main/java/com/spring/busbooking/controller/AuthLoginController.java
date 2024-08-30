package com.spring.busbooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.busbooking.dto.AuthenticationRequest;
import com.spring.busbooking.dto.AuthenticationResponse;
import com.spring.busbooking.model.Role;
import com.spring.busbooking.model.User;
import com.spring.busbooking.service.AuthService;
import com.spring.busbooking.service.JwtService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/login")
public class AuthLoginController {

	@Autowired
	private AuthService authService;
	
	@Autowired
    private JwtService jwtService;

	public AuthLoginController(AuthService authService) {
		super();
		this.authService = authService;
	}

	@GetMapping
	public String showLoginForm(Model model) {
		model.addAttribute("authRequest", new AuthenticationRequest());										
		return "login"; 
	}

	@PostMapping
	public String authenticateUser(@ModelAttribute AuthenticationRequest request, Model model,BindingResult result) {
		User authResponse = authService.authenticateRequest(request);
		String jwtString = jwtService.generateToken(authResponse);
		AuthenticationResponse authenticationResponse = new AuthenticationResponse();
		authenticationResponse.setAccessToken(jwtString);
		if(authResponse.getRole()==Role.ADMIN) {
			return "redirect:/admin";
		}
		return "redirect:/dashboard";
	}
	
	
	
}