package com.linocks.flight.dto;

import javax.validation.constraints.NotBlank;

import com.linocks.flight.entity.Airport;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AirportVM {
	private String id;
	@NotBlank
	private String code;
	private String location;
	@NotBlank
	private String name;

	public AirportVM(Airport airport) {
		this(airport.getId(), airport.getCode(), airport.getLocation(), airport.getName());
	}

}
