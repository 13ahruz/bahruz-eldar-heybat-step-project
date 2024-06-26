package az.edu.turing.stepProjBookingApp.service;

import az.edu.turing.stepProjBookingApp.model.dto.FlightDto;

import java.util.List;
import java.util.Optional;

public interface FlightService {
    List<FlightDto> getAllFlights();

    List<FlightDto> getAllByLocationIn24Hours(String destination);

    Optional<FlightDto> getFlightById(long id);

    boolean createFlight(FlightDto flightDto);
}
