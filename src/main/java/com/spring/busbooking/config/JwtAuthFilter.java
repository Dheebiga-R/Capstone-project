package com.spring.busbooking.config;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.spring.busbooking.service.JwtService;
import com.spring.busbooking.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter{

	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserService userService;
	
	public JwtAuthFilter(JwtService jwtService, UserService userService) {
		super();
		this.jwtService = jwtService;
		this.userService = userService;
	}
	
	
	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request,
			@NonNull HttpServletResponse response, 
			@NonNull FilterChain filterChain)
			throws ServletException, IOException {
		
		final String authHeader = request.getHeader("Authorization");
		final String email;
		final String jwtToken;
		
		if (io.micrometer.common.util.StringUtils.isEmpty(authHeader)
				|| !StringUtils.startsWith(authHeader, "Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		jwtToken = authHeader.substring(7);
		email = jwtService.extractUsername(jwtToken);

		if (io.micrometer.common.util.StringUtils.isEmpty(email) && SecurityContextHolder.getContext().getAuthentication()==null ) {
            UserDetails userDetails = userService.userDetailsService().loadUserByUsername(email);
            
            if(jwtService.isTokenValid(jwtToken,userDetails)) {
            	SecurityContext securityContext =  SecurityContextHolder.createEmptyContext();
            	
            	UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
            			userDetails,null, userDetails.getAuthorities());
            	
            	token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            	
            	securityContext.setAuthentication(token);
            	SecurityContextHolder.setContext(securityContext);
				
            }
		}

		filterChain.doFilter(request, response);
   }
}
