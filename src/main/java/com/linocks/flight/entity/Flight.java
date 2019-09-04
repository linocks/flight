package com.linocks.flight.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "flight")
public class Flight {
	private String id;
	private String number;
	private String origin;
	private String destination;
	private LocalDateTime departureDate;
	private LocalDateTime arrivalDate;
	private BigDecimal bronze;
	private BigDecimal silver;
	private BigDecimal gold;
	private String currency = "GHS";
	@DBRef
	private List<Seat> seats;
	private boolean fullyBooked = false;
}
