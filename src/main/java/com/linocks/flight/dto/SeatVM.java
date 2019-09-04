package com.linocks.flight.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.linocks.flight.entity.Seat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatVM {
	private String id;
	@NotBlank
	private String flightId;
	private String bookingId;
	@NotBlank
	private String number;
	@NotNull
	private BigDecimal price;
	private String currency = "GHS";
	private boolean available = true;

	public SeatVM(Seat seat) {
		this(seat.getId(), getFlightId(seat), getBookingClassId(seat), seat.getNumber(), seat.getPrice(),
				seat.getCurrency(), seat.isAvailable());
	}

	private static String getFlightId(Seat seat) {
		return seat.getFlight() != null ? seat.getFlight().getId() : null;
	}

	private static String getBookingClassId(Seat seat) {
		return seat.getBooking() != null ? seat.getBooking().getId() : null;
	}
}
