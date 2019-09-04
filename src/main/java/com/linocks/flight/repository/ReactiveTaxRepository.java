package com.linocks.flight.repository;

import com.linocks.flight.entity.Tax;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactiveTaxRepository extends ReactiveMongoRepository<Tax,String>{

}