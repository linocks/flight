package com.linocks.flight.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "airport")
public class Airport {
	private String id;
	private String code;
	private String location;
	private String name;
}
