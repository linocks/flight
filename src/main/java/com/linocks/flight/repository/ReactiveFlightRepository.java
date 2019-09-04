package com.linocks.flight.repository;

import com.linocks.flight.entity.Flight;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Mono;

public interface ReactiveFlightRepository extends ReactiveMongoRepository<Flight, String> {

    Mono<Flight> findById(String id);
    
}
