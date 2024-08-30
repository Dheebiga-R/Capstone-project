package com.spring.busbooking.dto;

import com.spring.busbooking.model.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Register {

	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private Role role;
}
