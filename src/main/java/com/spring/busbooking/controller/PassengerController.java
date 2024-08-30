package com.spring.busbooking.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
@PreAuthorize("hasrole('USER')")
public class PassengerController {

	@GetMapping
	public String getResult() {
		return "dashboard";
	}
}
