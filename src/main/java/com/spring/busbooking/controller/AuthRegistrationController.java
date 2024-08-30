package com.spring.busbooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.busbooking.dto.Register;
import com.spring.busbooking.model.Role;
import com.spring.busbooking.model.User;
import com.spring.busbooking.service.AuthService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/registration")
public class AuthRegistrationController {

	@Autowired
	private AuthService authService;
	
	public AuthRegistrationController(AuthService authService) {
		super();
		this.authService = authService;
	}

	@GetMapping
	public String showRegisterForm(Model model) {
		Register register =  new Register();
		register.setRole(Role.USER);
		model.addAttribute("register", register); 
		return "register";
	}

	@PostMapping
	public String registerUser(@Valid@ModelAttribute Register register, Model model,BindingResult result) {
		if(result.hasErrors()) {
			return "register";
		}
		User authResponse = authService.registerData(register);
		model.addAttribute("authResponse", authResponse);
		return "redirect:/login"; 
	}
}
