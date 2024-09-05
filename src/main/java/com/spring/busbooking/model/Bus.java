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
@Table(name = "bus")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bus {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="bus_id")
	private Integer id;
	
	@Column(name="bus_name",nullable = false)
	private String busName;
	
	@Column(name="bus_class",nullable = false)
	private String busClass;
	
	@Column(name="seat_count",nullable = false)
	private Integer seatCount;
	
	@Column(name="available_seat",nullable = false)
	private Integer availableSeat;
	
	@Column(name="terminal_from",nullable = false)
	private String terminalFrom;
	
	@Column(name="terminal_to",nullable = false)
	private String terminalTo;
	
	@Column(name="bus_fare",nullable = false)
	private Double busFare;
	
	@Column(name="arrival_time",nullable = false)
	private String arrivalTime;
	
	@Column(name="departure_time",nullable = false)
	private String departureTime;
	
	@Column(name="date",nullable = false)
	private String date;
	
	@OneToMany(mappedBy = "bus")
	private List<Booking> bookings;
}