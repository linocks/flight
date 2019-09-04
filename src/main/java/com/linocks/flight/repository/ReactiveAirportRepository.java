package com.linocks.flight.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.linocks.flight.entity.Airport;

public interface ReactiveAirportRepository extends ReactiveCrudRepository<Airport, String> {

}
