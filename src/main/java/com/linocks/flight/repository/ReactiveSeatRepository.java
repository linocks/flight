package com.linocks.flight.repository;

import com.linocks.flight.dto.FlightVM;
import com.linocks.flight.dto.SeatVM;
import com.linocks.flight.entity.Seat;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReactiveSeatRepository extends ReactiveMongoRepository<Seat, String> {
	@Query("{flightId : ?0}")
	Flux<Seat> findByFlight(Mono<FlightVM> fl);
	
	@Query("{flightId : ?0}")
	Flux<SeatVM> findByFlightId(String flightId);
}
