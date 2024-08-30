package com.spring.busbooking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.spring.busbooking.model.Role;
import com.spring.busbooking.model.User;
import com.spring.busbooking.repository.UserRepository;

@SpringBootApplication
public class BusbookingApplication implements CommandLineRunner{

	@Autowired
	private UserRepository userRepository;
	
	
	public BusbookingApplication(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(BusbookingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User user = userRepository.findByRole(Role.ADMIN);
		
		if(user==null) {
			User user2 = User.builder()
					.firstName("Admin")
					.lastName("Controller")
					.email("admin.controller@gmail.com")
					.password(new BCryptPasswordEncoder().encode("@123"))
					.role(Role.ADMIN)
					.build();
			userRepository.save(user2);
		}
		
	}

}
