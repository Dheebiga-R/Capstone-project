package com.spring.busbooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.busbooking.model.Bus;
import com.spring.busbooking.repository.BusRepository;
import com.spring.busbooking.service.AdminService;
import com.spring.busbooking.validation.BusNotFoundException;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private BusRepository busRepository;
	
	@Autowired
	private AdminService adminService;
	
	//Admin controller, where admin can add update and delete bus route
	public AdminController(BusRepository busRepository, AdminService adminService) {
		this.busRepository = busRepository;
		this.adminService = adminService;
	}

	@GetMapping
	public String getmethod(Model model) {
		model.addAttribute("buses", adminService.getAllBuses());
		return "admin";
	}
	
	@GetMapping("/add")
	public String addRoute(Model model) {
		model.addAttribute("bus", new Bus());
		return "admin_add_route";
	}
	
	@PostMapping("/add")
	public String processRoute(@ModelAttribute @Valid Bus bus,BindingResult result,Model model) {
		if(result.hasErrors()) {
			return "redirect:/admin/add";
		}
		busRepository.save(bus);
		return "redirect:/admin";
	}
	
	@RequestMapping("/delete/{id}")
	public String deleteRoute(@PathVariable("id") Integer id) {
		busRepository.deleteById(id);
		return "redirect:/admin";
	}
	
	@GetMapping("/update/{id}")
	public String updateRoute(@PathVariable("id") Integer id,Model model) {
		model.addAttribute("bus",adminService.findBusById(id));
		return "admin_update_route";
	}
	
	@PostMapping("/update/{id}")
	public String updateProcess(@PathVariable Integer id,@ModelAttribute("bus")@Valid Bus bus,BindingResult result,Model model) throws BusNotFoundException {
		if(result.hasErrors()) {
			return "admin_update_route";
		}
		Bus bus2 = adminService.findBusById(id);
		if(bus2==null) {
			throw new BusNotFoundException("Bus is not present");
		}
		bus2.setBusName(bus.getBusName());
		bus2.setBusClass(bus.getBusClass());
		bus2.setBusFare(bus.getBusFare());
		bus2.setArrivalTime(bus.getArrivalTime());
		bus2.setDepartureTime(bus.getDepartureTime());
		bus2.setDate(bus.getDate());
		bus2.setBookings(bus.getBookings());
		bus2.setSeatCount(bus.getSeatCount());
		bus2.setAvailableSeat(bus.getAvailableSeat());
		bus2.setTerminalFrom(bus.getTerminalFrom());
		bus2.setTerminalTo(bus.getTerminalTo());
		adminService.updateBus(bus2);
		return "redirect:/admin";
	}
	
	
	
}
