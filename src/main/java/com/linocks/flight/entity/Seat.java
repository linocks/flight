package com.linocks.flight.entity;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
// @CompoundIndex(name = "unique_seat_idx", def = "{'number': 1, 'flight': 1}", unique = false)
public class Seat {
	@Id
	private String id;
	private String number;
	@DBRef
	private Flight flight;
	@DBRef
	private BookingClass booking;
	private BigDecimal price;
	private String currency="GHS";
	private boolean available = true;
}
