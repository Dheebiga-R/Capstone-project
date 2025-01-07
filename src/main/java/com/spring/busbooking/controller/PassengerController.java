package com.spring.busbooking.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.busbooking.dto.AuthenticationRequest;
import com.spring.busbooking.dto.BookingDto;
import com.spring.busbooking.model.Booking;
import com.spring.busbooking.model.Bus;
import com.spring.busbooking.model.Passenger;
import com.spring.busbooking.model.User;
import com.spring.busbooking.repository.UserRepository;
import com.spring.busbooking.service.AdminService;
import com.spring.busbooking.service.AuthService;
import com.spring.busbooking.service.PassengerService;
import com.spring.busbooking.validation.BusNotFoundException;
import com.spring.busbooking.validation.PassengerNotFoundException;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/booking")
public class PassengerController {
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private PassengerService passengerService;
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private UserRepository userRepository;

	public PassengerController(AdminService adminService,PassengerService passengerService,
			AuthService authService,UserRepository userRepository) {
		this.adminService = adminService;
		this.passengerService = passengerService;
		this.authService = authService;
		this.userRepository = userRepository;
	}
	
	//passenger controller , where passenger can search buses and see their profile, add passengers and book the ticket
	@GetMapping
	public String getAll(Model model,Bus bus) {
		model.addAttribute("buses", adminService.getAllBuses());
		return "dashboard";
	}

	@GetMapping("/search")
	public String getResult(@RequestParam(name = "from", required = false) String from,
			@RequestParam(name = "to", required = false) String to,
			@RequestParam(name = "date", required = false) String date,Model model) {
		List<Bus> buses;
		if(from!=null && !from.isEmpty() && to!=null & !to.isEmpty() && date!=null && !date.isEmpty()) {
			buses = adminService.findByTerminalFromAndTerminalToAndDate(from,to,date);
		}else {
			buses = adminService.getAllBuses();
		}
		model.addAttribute("buses",buses );
		return "passenger_search";
	}
	
	@GetMapping("/passengers")
	public String getallPassengers(Model model) {
		model.addAttribute("passengers", passengerService.getAllPassengers());
		return "passengers";
	}
	
	@GetMapping("/add")
	public String addPassenger(Model model) {
		model.addAttribute("passenger", new Passenger());
		return "passenger_add";
	}
	
	@PostMapping("/add")
	public String processPassenger(@ModelAttribute("passenger") @Valid Passenger passenger,BindingResult result) {
		if(result.hasErrors()) {
			return "passenger_add";
		}
		passengerService.savePassenger(passenger);
		return "redirect:/booking/passengers";
	}
	
	@GetMapping("/update/{id}")
	public String updatepassenger(@PathVariable("id") Integer id,Model model) {
		model.addAttribute("passenger",passengerService.findPassengerById(id));
		return "passenger_update";
	}
	
	@PostMapping("/update/{id}")
	public String updateProcess(@PathVariable Integer id,@ModelAttribute("passenger")@Valid Passenger passenger,BindingResult result,Model model) throws PassengerNotFoundException {
		if(result.hasErrors()) {
			return "passenger_update";
		}
		Passenger passenger2 = passengerService.findPassengerById(id);
		if(passenger2==null) {
			throw new PassengerNotFoundException("passenger is not present");
		}
		passenger2.setId(id);
		passenger2.setName(passenger.getName());
		passenger2.setAge(passenger.getAge());
		passenger2.setContactDetails(passenger.getContactDetails());
		passengerService.update(passenger2);
		return "redirect:/booking/passengers";
	}
	
	@GetMapping("/getuser")
	public String getUser(Principal p,Model model)
	{
		User user = (User) adminService.findByEmail(p.getName());
		
		model.addAttribute("user",user);
		return "profile";
     }
	
	@GetMapping("/book")
	public String getBooking(Model model) {
		BookingDto booking = new BookingDto();
		model.addAttribute("book", booking);
		return "ticket_booking";
	}
	
	@PostMapping("/processBook")
	public String booked(@Valid BookingDto bookingDto,
			BindingResult result,Model model) 
					throws BusNotFoundException, PassengerNotFoundException {
		if(result.hasErrors()) {
			return "ticket_booking";
		}
		passengerService.booking(bookingDto);
		return "redirect:/booking/search";
	}
	
	@GetMapping("/history")
	public String historyPage(Model model) {
		model.addAttribute("bookings", passengerService.getAllBookings());
	    return "passenger_history";
	}
	
	@GetMapping("/ticket/{id}")
	public String viewTicket(@PathVariable("id") Integer id,Booking booking,Model model) {
		model.addAttribute("book", passengerService.findBookingById(id));
		return "ticket";
	}
}
	
	



































	
	
	
	
