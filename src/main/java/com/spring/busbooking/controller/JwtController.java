package com.spring.busbooking.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.spring.busbooking.dto.AuthenticationRequest;
import com.spring.busbooking.model.Role;
import com.spring.busbooking.model.User;
import com.spring.busbooking.repository.UserRepository;
import com.spring.busbooking.service.JwtService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;



@Controller
public class JwtController {
	
	private UserDetailsService userDetailsService;
	
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserRepository userRepository;
	
	public JwtController(UserDetailsService userDetailsService, JwtService jwtService,
			AuthenticationManager authenticationManager,UserRepository userRepository) {
		this.userDetailsService = userDetailsService;
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
	}
		
	//generate token
	@PostMapping("/token") 
	
	public String generateToken(Model m,HttpSession session,
			@ModelAttribute AuthenticationRequest request, HttpServletResponse res) throws Exception
	{
		System.out.println(request);
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		} catch (UsernameNotFoundException e) {
			
			session.setAttribute("msg","Bad Credentials");
			return "redirect:/login";
		
		} catch(BadCredentialsException e)
		{
			session.setAttribute("msg","Bad Credentials");
			return "redirect:/login";
		}
		
		// fine area..
		
		try {
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
		
		
		
		System.out.println("userDetails.getUsername: "   +userDetails.getUsername());
		
	
	final String token = jwtService.generateToken(userDetails);
	
	
	Cookie cookie = new Cookie("token",token);
	cookie.setMaxAge(Integer.MAX_VALUE);
	res.addCookie(cookie);
	
	
	System.out.println("token:: " + token);
	
	Optional<User> user = userRepository.findByEmail(request.getEmail());
	if(user.get().getRole()==Role.ADMIN) {
		return "redirect:/admin";
	}
	return "redirect:/booking";
		}catch(Exception e)
		{
			session.setAttribute("msg","Credentials were right But something went wrong!!");
			return "redirect:/login";
		}
	}
	
	
	 @GetMapping("/logout")
	    public String logout(HttpServletRequest request,HttpServletResponse res,Model m,HttpSession session) {
	       
		 
		 String msg = null;

		 Cookie[] cookies2 = request.getCookies();
		 for(int i = 0; i < cookies2.length; i++) 
		 {
		 	if (cookies2[i].getName().equals("token")) 
		 	{
		     cookies2[i].setMaxAge(0);
		     res.addCookie(cookies2[i]);
		 	msg = "Logout successfully";

		  }
	       
	    }
		 session.setAttribute("msg", msg);
		 

	        return "redirect:/login";

	 }
	 
}
