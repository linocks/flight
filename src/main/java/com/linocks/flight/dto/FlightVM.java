package com.linocks.flight.dto;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.linocks.flight.entity.Flight;
import com.linocks.flight.entity.Seat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FlightVM {
	private String id;
	@NotBlank
	private String number;
	@NotBlank
	private String origin;
	@NotBlank
	private String destination;
	@NotNull
	private LocalDateTime departureDate;
	private LocalDateTime arrivalDate;
	private BigDecimal bronze;
	private BigDecimal silver;
	private BigDecimal gold;
	private Duration duration;
	private String currency = "GHS";
	private boolean fullyBooked = false;
	private List<Seat> seats;
	

	
	public FlightVM(Flight flight) {
		this(
				flight.getId(), //
				flight.getNumber(), //
				flight.getOrigin(), //
				flight.getDestination(), //
				flight.getDepartureDate(), //
				flight.getArrivalDate(), //
				flight.getBronze(), //
				flight.getSilver(), //
				flight.getGold(), //
				calculateDuration(flight), //
				flight.getCurrency(), //
				flight.isFullyBooked(), flight.getSeats());
	}
	
	private static Duration calculateDuration(Flight flight) {
		if(flight.getDepartureDate() == null || flight.getArrivalDate() == null)
			return Duration.ZERO;
		else
			return Duration.between(flight.getArrivalDate(), flight.getDepartureDate());
	}

	// public LocalDateTime convertDateTime(String dateTime){
	// 	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:ss");

	// 	return LocalDateTime.parse(dateTime, formatter;)

	// }

}
