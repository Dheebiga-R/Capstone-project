package com.spring.busbooking.config;

import java.io.IOException;
import java.io.OutputStream;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.busbooking.validation.ExceptionMessage;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler{

	//Access denied class
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		
		ExceptionMessage e = new ExceptionMessage("Access denied", false);
	
		OutputStream out = response.getOutputStream();
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(out, e);
		out.flush();
	
	}
}
