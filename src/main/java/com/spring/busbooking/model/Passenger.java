package com.spring.busbooking.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="passenger")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Passenger {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="passenger_name")
	private String name;
	
	@Column(name="passenger_age")
	private int age;
	
	@Column(name="passenger_detail")
	private String contactDetails;
	
	@OneToMany(mappedBy = "passenger")
	private List<Booking> bookings;
}
