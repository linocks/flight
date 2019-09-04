package com.linocks.flight.dto;

import javax.validation.constraints.NotBlank;

import org.springframework.data.mongodb.core.mapping.Document;

import com.linocks.flight.entity.BookingClass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class BookingClassVM {
	private String id;
	@NotBlank
	private String type;

	public BookingClassVM(BookingClass booking) {
		this(booking.getId(), booking.getType());
	}
}
