package com.spring.busbooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.busbooking.model.Bus;

@Repository
public interface BusRepository extends JpaRepository<Bus, Integer>{

	List<Bus> findByTerminalFromAndTerminalToAndDate(String from, String to, String date);

	List<Bus> findByTerminalFrom(String from);

	List<Bus> findByTerminalTo(String to);

}
