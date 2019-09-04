package com.linocks.flight.api;

import java.time.LocalDateTime;

import com.linocks.flight.dto.AirportVM;
import com.linocks.flight.dto.BookingClassVM;
import com.linocks.flight.dto.FlightVM;
import com.linocks.flight.dto.SeatVM;
import com.linocks.flight.entity.Flight;
import com.linocks.flight.entity.Seat;
import com.linocks.flight.repository.ReactiveAirportRepository;
import com.linocks.flight.repository.ReactiveBookingClassRepository;
import com.linocks.flight.repository.ReactiveFlightRepository;
import com.linocks.flight.repository.ReactiveSeatRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class CharterResource {
	private final ReactiveAirportRepository airportRepository;
	private final ReactiveFlightRepository flightRepository;
	private final ReactiveSeatRepository seatRepository;
	private final ReactiveBookingClassRepository bookingClassRepository;

	@Autowired
	ReactiveMongoTemplate mongoOps;

	@GetMapping("/airports")
	public Flux<AirportVM> fetchAirports() {
		return airportRepository.findAll().map(AirportVM::new);
	}

	@GetMapping("/airports/{id}")
	public Mono<AirportVM> fetchAirport(@PathVariable String id) {
		return airportRepository.findById(id).map(AirportVM::new);
	}

	@GetMapping("/flights")
	public Flux<FlightVM> fetchFlights() {
		return flightRepository.findAll().map(FlightVM::new);
	}

	@GetMapping("/available-flights")
	public Flux<FlightVM> getAvailableCharter() {
		Query q = new Query();
		q.addCriteria(Criteria.where("fullyBooked").is(false).and("departureDate").gt(LocalDateTime.now()));

		return mongoOps.find(q, Flight.class).map(FlightVM::new);

	}

	@GetMapping("/flights/{id}")
	public Mono<FlightVM> fetchFlight(@PathVariable String id) {
		return flightRepository.findById(id).map(FlightVM::new);
	}

	@PostMapping("seats/{seatId}")
	public Mono<SeatVM> bookSeat(@PathVariable String seatId) {
		return mongoOps.findById(seatId, Seat.class) //
				.flatMap(seat -> {
					seat.setAvailable(false);
					return mongoOps.save(seat);
				}).flatMap(seat -> {
					Query q0 = new Query();
					q0.addCriteria(Criteria.where("flight").is(seat.getFlight()).and("available").is(true));

					return mongoOps.count(q0, Seat.class);
				}).filter(count -> count.intValue() < 1).flatMap(count -> {
					return mongoOps.findById(seatId, Seat.class).flatMap(seat -> {
						Flight flight = seat.getFlight();
						flight.setFullyBooked(true);
						return mongoOps.save(flight);
					});
				}).map(flight -> new SeatVM());
	}

	@PostMapping("/unbook-seat/{seatId}")
	public Mono<SeatVM> undoBookSeat(@PathVariable String seatId) {

		Mono<SeatVM> svm = mongoOps.findById(seatId, Seat.class).flatMap(s -> {

			s.setAvailable(true);
			Flight f = s.getFlight();
			// update flight information
			f.setFullyBooked(false);
			mongoOps.save(f);
			return mongoOps.save(s).map(SeatVM::new);

		});

		return svm;
	}

	@GetMapping("/seats")
	public Flux<SeatVM> getAllSeats() {

		return seatRepository.findAll().map(SeatVM::new);
	}

	@GetMapping("seats/{flightId}")
	public Flux<SeatVM> allSeats(@PathVariable String flightId) {
		System.out.println("getting seats for flight: " + flightId);
		return seatRepository.findByFlightId(flightId);
	}

	@GetMapping("/booking-classes")
	public Flux<BookingClassVM> fetchBookingClasses() {
		return bookingClassRepository.findAll().map(BookingClassVM::new);
	}

	@GetMapping("/booking-classes/{id}")
	public Mono<BookingClassVM> fetchBookingClass(@PathVariable String id) {
		return bookingClassRepository.findById(id).map(BookingClassVM::new);
	}
}
