package com.linocks.flight.api;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.linocks.flight.dto.AirportVM;
import com.linocks.flight.dto.BookingClassVM;
import com.linocks.flight.dto.FlightVM;
import com.linocks.flight.dto.SeatVM;
import com.linocks.flight.entity.Airport;
import com.linocks.flight.entity.BookingClass;
import com.linocks.flight.entity.Flight;
import com.linocks.flight.entity.Seat;
import com.linocks.flight.repository.ReactiveAirportRepository;
import com.linocks.flight.repository.ReactiveBookingClassRepository;
import com.linocks.flight.repository.ReactiveFlightRepository;
import com.linocks.flight.repository.ReactiveSeatRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

// TODO Update and Delete
@RestController
@RequiredArgsConstructor
@RequestMapping("admin")
public class CharterAdministrationResource {
	private final ReactiveAirportRepository airportRepository;
	private final ReactiveFlightRepository flightRepository;
	private final ReactiveSeatRepository seatRepository;
	private final ReactiveBookingClassRepository bookingRepository;

	@Autowired
	ReactiveMongoTemplate mongoOps;

	@PostMapping("/flights")
	public Mono<FlightVM> createFlight(@Valid @RequestBody FlightVM flight) {
		Flight f = new Flight();
		f.setOrigin(flight.getOrigin());
		f.setNumber(flight.getNumber());
		f.setDestination(flight.getDestination());
		f.setDepartureDate(flight.getDepartureDate());
		f.setArrivalDate(flight.getArrivalDate());
		f.setCurrency(flight.getCurrency());
		f.setBronze(flight.getBronze());
		f.setSilver(flight.getSilver());
		f.setGold(flight.getGold());

		return flightRepository.save(f).map(FlightVM::new);
	}

	@PostMapping("/airports")
	public Mono<AirportVM> createAirport(@Valid @RequestBody AirportVM airport) {
		Airport a = new Airport();
		a.setCode(airport.getCode());
		a.setLocation(airport.getLocation());
		a.setName(airport.getName());

		return airportRepository.save(a).map(AirportVM::new);
	}

	@GetMapping("/all-airports")
	public Flux<AirportVM> getAllAirports() {
		System.out.println(LocalDateTime.now());
		return airportRepository.findAll().map(AirportVM::new);
	}

	@PostMapping("/seat")
	public Mono<SeatVM> createSeat(@Valid @RequestBody SeatVM seat) {
		Mono<Flight> flight = flightRepository.findById(seat.getFlightId());

		flight = flight.map(f->{
			f.setFullyBooked(false);
			return f;
		});

		Mono<BookingClass> booking = bookingRepository.findById(seat.getBookingId());

		Seat s = new Seat();
		s.setCurrency(seat.getCurrency());
		s.setNumber(seat.getNumber());

		flight.subscribe(f -> s.setFlight(f));

		booking.subscribe(b -> s.setBooking(b));
		s.setPrice(seat.getPrice());

		mongoOps.save(flight);

		return seatRepository.save(s).map(SeatVM::new);
	}

	@PostMapping("/seats")
	public Flux<SeatVM> createSeats(@Valid @RequestBody List<SeatVM> seats) {
		List<Seat> seatList = new ArrayList<>();
		seats.forEach(s->{
			Seat seat = new Seat();
			Mono<Flight> flight = flightRepository.findById(s.getFlightId());	
			Mono<BookingClass> booking = bookingRepository.findById(s.getBookingId());

			booking.subscribe(b->seat.setBooking(b));
			flight.subscribe(f -> seat.setFlight(f));

			seat.setPrice(s.getPrice());
			seat.setNumber(s.getNumber());
			seatList.add(seat);
		});

		if(seatList.get(0).getFlight()!=null){

			Flight f = seatList.get(0).getFlight();
	
			f.setFullyBooked(false);
			mongoOps.save(f);		
		}
		seatList.forEach(System.out::println);
		return seatRepository.saveAll(seatList).map(SeatVM::new);
	}

	@GetMapping("/flights/{id}")
	public Mono<FlightVM> fetchFlight(@PathVariable String id) {
		return flightRepository.findById(id).map(FlightVM::new);
	}

	@GetMapping("/seats/{flightId}")
	public Flux<SeatVM> fetchSeats(@PathVariable String flightId) {

		return seatRepository.findByFlightId(flightId).map(s -> {
			SeatVM svm = new SeatVM();
			svm.setId(s.getId());
			svm.setBookingId(s.getBookingId());
			svm.setFlightId(flightId);
			svm.setNumber(s.getNumber());
			svm.setPrice(s.getPrice());
			return svm;
		});

	}

	@PostMapping("/bookings")
	public Flux<BookingClass> createBookings(@Valid @RequestBody List<BookingClass> bookings) {				
		return bookingRepository.saveAll(bookings);
	}

	@PostMapping("/booking")
	public Mono<BookingClassVM> createBooking(@Valid @RequestBody BookingClassVM booking) {
		BookingClass b = new BookingClass();
		b.setType(booking.getType());
		return bookingRepository.save(b).map(BookingClassVM::new);
	}

	@GetMapping("/bookings")
	public Flux<BookingClassVM> getAllBookings() {
		return bookingRepository.findAll().map(BookingClassVM::new);
	}
}
