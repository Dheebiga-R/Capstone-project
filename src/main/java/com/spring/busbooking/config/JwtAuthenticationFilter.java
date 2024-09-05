package com.spring.busbooking.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import com.spring.busbooking.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserDetailsService userDetailsService;
	
	public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
		this.jwtService = jwtService;
		this.userDetailsService = userDetailsService;
	}
	/*Authentication filter to generate token from cookies*/
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		if (request.getRequestURI().startsWith("/bus")) {
            filterChain.doFilter(request, response);
            return;
        }
		
		if (request.getRequestURI().startsWith("/register")) {
            filterChain.doFilter(request, response);
            return;
        }
		
		if (request.getRequestURI().startsWith("/login")) {
            filterChain.doFilter(request, response);
            return;
        }
		
		if (request.getRequestURI().startsWith("/token")) {
            filterChain.doFilter(request, response);
            return;
        }
		
		String t = null;
		if(request.getCookies()!=null)
		{
			Cookie[]rc =request.getCookies();
			
			for(int i=0;i<rc.length;i++)
			{
				if(rc[i].getName().equals("token")==true)
				{
					
				t = rc[i].getValue().toString();
				}
			}
			System.out.println(rc);
		}
		
		
	String requestTokenHeader = "Bearer "+t;
	
	System.out.println("================================================================================================================================");
	System.out.println("requestTokenHeader: " + requestTokenHeader);
	System.out.println("================================================================================================================================");

	
	String username = null;
	String jwtToken = null;
	if(requestTokenHeader!= null && requestTokenHeader.startsWith("Bearer "))
	{
		jwtToken = requestTokenHeader.substring(7); // it will remove Bearer from
		
		System.out.println("===================================================================");
		System.out.println("jwtToken: " + jwtToken);
		System.out.println("===================================================================");
		
		try {
			username = jwtService.extractUsername(jwtToken);
			System.out.println("username: " + username);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
		 //security
		if(username!= null && SecurityContextHolder.getContext().getAuthentication() == null) 
		{
			 UserDetails userDetails = userDetailsService.loadUserByUsername(username);
 
			 System.out.println("name: "+ userDetails.getUsername());
			 System.out.println("password: "+ userDetails.getPassword());
			 
			 if(jwtService.isTokenValid(jwtToken, userDetails))
			 {
				
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =	new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				
			
			}
			else {
				System.out.println("Token is not validate");
			}
		
		
	}
	filterChain.doFilter(request, response);
		
		
	}

}
