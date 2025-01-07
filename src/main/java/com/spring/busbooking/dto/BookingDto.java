package com.spring.busbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {
	
	//booking dto
	private Integer seatCount;

    private Integer busId;

    private Integer passengerId;
}
