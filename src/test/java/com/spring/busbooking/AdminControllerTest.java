package com.spring.busbooking;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.spring.busbooking.model.Passenger;
import com.spring.busbooking.repository.PassengerRepository;

import com.spring.busbooking.service.PassengerServiceImpl;

@ExtendWith(MockitoExtension.class)
public class AdminControllerTest {

    @Mock
		private PassengerRepository passengerRepository;
		
		@InjectMocks
		private PassengerServiceImpl passengerServiceImpl;
		
		private Optional<Passenger> passenger;
		
		@BeforeEach
		public void setup() {
			passenger = Optional.of(Passenger.builder()
					.id(1)
					.age(45)
					.contactDetails("3452678532")
					.name("dheebiga")
					.build());
			
		}
		
		
		@DisplayName("Junit test for find passenger by id")
		@Test
		public void findPassengerById_foundReturn() {
			given(passengerRepository.findById(passenger.get().getId()))
			.willReturn(passenger);
			Passenger passenger2 = passengerServiceImpl.findPassengerById(passenger.get().getId());
			assertThat(passenger2).isNotNull();
		}
		
		@DisplayName("JUnit test for savePassenger method which throws exception")
	    @Test
	    public void givenExistingId_whenSavePassenger_thenThrowsException(){
	        // given - precondition or setup
	        given(passengerRepository.findById(passenger.get().getId()))
	                .willReturn(passenger);

	        System.out.println(passengerRepository);
	        System.out.println(passengerServiceImpl);

	        // when -  action or the behaviour that we are going test
	        org.junit.jupiter.api.Assertions.assertThrows(Exception.class, () -> {
	            passengerServiceImpl.savePassenger(passenger);
	        });

	        // then
	        verify(passengerRepository, never()).save(any(Passenger.class));
	    }
		
		@DisplayName("JUnit test for getAllPassengers method")
	    @Test
	    public void givenPassengersList_whenGetAllPassengers_thenReturnPassengerList(){
	        // given - precondition or setup

	        Passenger passenger1 = Passenger.builder()
	        		             .id(2)
	        		             .age(23)
	        		             .contactDetails("9844567321")
	        		             .name("Gurunath")
	        		             .build();
	        
	        Passenger passenger2 = Passenger.builder()
		             .id(1)
		             .age(43)
		             .contactDetails("9944567321")
		             .name("Ashok")
		             .build();

	        given(passengerRepository.findAll()).willReturn(List.of(passenger1, passenger2));

	        // when -  action or the behaviour that we are going test
	        List<Passenger> PassengerList = passengerServiceImpl.getAllPassengers();

	        // then - verify the output
	        assertThat(PassengerList).isNotNull();
	        assertThat(PassengerList.size()).isEqualTo(2);
	    }
}

