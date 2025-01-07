package com.spring.busbooking.controller;

import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.busbooking.dto.AuthenticationRequest;
import com.spring.busbooking.dto.AuthenticationResponse;
import com.spring.busbooking.dto.Register;
import com.spring.busbooking.model.Role;
import com.spring.busbooking.model.User;
import com.spring.busbooking.repository.UserRepository;
import com.spring.busbooking.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
public class AuthRegistrationController {
	@Autowired
	private AuthService authService;

	@Autowired
	private UserRepository userRepository;
	
	public AuthRegistrationController(AuthService authService,UserRepository userRepository) {
		this.authService = authService;
		this.userRepository = userRepository;
	}
	
	//for user registration and login
	@GetMapping("/bus")
	public String homepage() {
		return "home";
	}
	
	@GetMapping("/register")
	public String registerUser(Model model) {
		model.addAttribute("register", new Register());
		return "register";
	}
	
	@PostMapping("/register")
	public String processRegister(@Valid Register register,BindingResult result) {
		if(result.hasErrors()) {
			return "register";
		}
		authService.registerData(register);
		return "redirect:/login?success";
	}
	
	@GetMapping("/login")
	public String loginUser(Model model) {
		model.addAttribute("authRequest", new AuthenticationRequest());
		return "login";
	}

	
	/*@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> registerEntity(@RequestBody Register register){
		return ResponseEntity.ok(authService.registerData(register));
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> loginEntity(@RequestBody AuthenticationRequest request){
		return ResponseEntity.ok(authService.authenticateRequest(request));
	}*/
}
