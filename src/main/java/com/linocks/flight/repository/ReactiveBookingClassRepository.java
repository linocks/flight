package com.linocks.flight.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.linocks.flight.entity.BookingClass;

public interface ReactiveBookingClassRepository extends ReactiveCrudRepository<BookingClass, String> {

}
