package com.spring.busbooking.config;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.spring.busbooking.repository.UserRepository;
import com.spring.busbooking.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
public class JwtAuthFilter extends OncePerRequestFilter{

	@Autowired
	private JwtService jwtService;

	private UserDetailsService userDetailsService;
	
	public JwtAuthFilter(JwtService jwtService,UserDetailsService userDetailsService) {
		this.jwtService = jwtService;
		this.userDetailsService=userDetailsService;
	}
	
	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request,
			@NonNull HttpServletResponse response, 
			@NonNull FilterChain filterChain)
			throws ServletException, IOException {
		
		final String authHeader = request.getHeader("Authorization");
		final String email;
		final String jwtToken;
		
		if (authHeader==null
				|| !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		jwtToken = authHeader.substring(7);
		email = jwtService.extractUsername(jwtToken);

		if (email!=null && SecurityContextHolder.getContext().getAuthentication()==null ) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            	
            	UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
            			userDetails,null, userDetails.getAuthorities());
            	
            	token.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
            	
            	SecurityContextHolder.getContext().setAuthentication(token);
				
            }

		filterChain.doFilter(request, response);
   }
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return request.getServletPath().contains("/bus");
	}
}
