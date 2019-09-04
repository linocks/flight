package com.linocks.flight.repository;

import com.linocks.flight.entity.Charges;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Mono;

@Repository
public interface ReactiveChargesReposistory extends ReactiveMongoRepository<Charges,String>{

    @Query("{'taxId': ?0}")
	Mono<Charges> findByTaxId(String taxId);

}