package com.linocks.flight.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "booking")
public class BookingClass {
	private String id;
	// @Indexed(unique = true)
	private String type;
}
